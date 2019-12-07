package springtest;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import springtest.model.Utilisateur;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//cf. https://www.baeldung.com/spring-security-integration-tests
//https://dzone.com/articles/spring-boot-with-embedded-mongodb
//  https://stackoverflow.com/questions/42369467/use-embedded-database-for-test-in-spring-boot

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
//@AutoConfigureTestDatabase
public class Full_WithMockMVC_Test {

    @LocalServerPort
    private int port;

   // private MockMvc mvc;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private MongoTemplate mongoTemplate;

//    @Before
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(webAppContext)
//               // .apply(springSecurity())
//                .build();
//    }

    @PostConstruct
    public void setup() {
        Utilisateur user = new Utilisateur();
        user.setEmail("toto");
        user.setPassword("tutu");
        mongoTemplate.save(user);
    }

    @Test
    //@WithMockUser("user@yopmail.com")
    //@WithUserDetails("toto")
    public void testWithFrenchIsbn() throws Exception {


        BeanUtils.logBeans(appContext);

        // given


        // when + then
       // mvc.perform(get("/api/param")).andExpect(content().string(containsString("toto")));
        String forObject = testRestTemplate.withBasicAuth("toto", "tutu").getForObject("http://localhost:" + port + "/api/value", String.class);

        System.out.println(forObject);
    }

    @Configuration
    static class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            System.out.println("security being set");
            http
                    .authorizeRequests()
                    .anyRequest().permitAll()
                    .and()
                    .csrf().disable();
        }
    }

}