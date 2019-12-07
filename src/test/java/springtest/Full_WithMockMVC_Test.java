package springtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import springtest.model.Utilisateur;

import javax.annotation.PostConstruct;
// cf. https://www.baeldung.com/spring-security-integration-tests
// https://dzone.com/articles/spring-boot-with-embedded-mongodb
//  https://stackoverflow.com/questions/42369467/use-embedded-database-for-test-in-spring-boot

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class Full_WithMockMVC_Test {

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
        user.setPassword("tutu");
        mongoTemplate.save(user);
    }

    @Test
    public void testWithFrenchIsbn() throws Exception {
        String forObject = testRestTemplate.withBasicAuth("toto", "tutu").getForObject("http://localhost:" + port + "/api/value", String.class);
        // TODO : My Controller is not called :(
        System.out.println(forObject);
    }

}