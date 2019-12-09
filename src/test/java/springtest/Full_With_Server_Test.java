package springtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import springtest.model.Contact;
import springtest.model.Utilisateur;

import static org.junit.Assert.assertEquals;
// Articles that help me :
// https://www.baeldung.com/spring-security-integration-tests
// https://dzone.com/articles/spring-boot-with-embedded-mongodb
// https://stackoverflow.com/questions/42369467/use-embedded-database-for-test-in-spring-boot

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class Full_With_Server_Test {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setup() {
        // save user who makes authentication
        Utilisateur user = new Utilisateur();
        user.setEmail("toto");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String p = bCryptPasswordEncoder.encode("tutu");
        user.setPassword(p);
        mongoTemplate.save(user);

        // save contact
        Contact contact = new Contact();
        contact.setAddress("adress2");
        contact.setUserId("456");
        mongoTemplate.save(contact);

    }

    @Test
    public void getUserAdress() {
        String adress = testRestTemplate.withBasicAuth("toto", "tutu").getForObject("http://localhost:" + port + "/users/456", String.class);
        assertEquals(adress, "adress2");
    }

}