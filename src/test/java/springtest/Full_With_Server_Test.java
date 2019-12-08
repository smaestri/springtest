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
import springtest.model.Utilisateur;

import static org.junit.Assert.assertEquals;
// cf. https://www.baeldung.com/spring-security-integration-tests
// https://dzone.com/articles/spring-boot-with-embedded-mongodb
//  https://stackoverflow.com/questions/42369467/use-embedded-database-for-test-in-spring-boot

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
        Utilisateur user = new Utilisateur();
        user.setEmail("toto");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String p = bCryptPasswordEncoder.encode("tutu");
        user.setPassword(p);
        mongoTemplate.save(user);
    }

    @Test
    public void testWithFrenchIsbn() throws Exception {
        String forObject = testRestTemplate.withBasicAuth("toto", "tutu").getForObject("http://localhost:" + port + "/api/value", String.class);
        assertEquals(forObject, "toto");
    }

}