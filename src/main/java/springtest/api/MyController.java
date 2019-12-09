package springtest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public String test(@PathVariable("userId") String userId ) throws Exception {
        return contactService.getAdress(userId);
    }
}
