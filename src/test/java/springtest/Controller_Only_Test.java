package springtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springtest.api.MyController;
import springtest.security.CustomUserDetailsService;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(MyController.class)
public class Controller_Only_Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext appContext;

    // need to mock this beans, will not be used anyway (we use @WithMockUser below)
    @MockBean
    CustomUserDetailsService service;

    @Test
    @WithMockUser // use with mockMvc only
    public void testController() throws Exception {
        // given
        String param = "9782253066200";

        // when then
        mockMvc.perform(get("/api/" + param)).andExpect(content().string(containsString("toto")));

    }

}