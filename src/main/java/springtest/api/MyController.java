package springtest.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @RequestMapping(value = "/api/{param}", method = RequestMethod.GET)
    public String test(@PathVariable("param") String param ) {
        System.out.print("Controller called with param " + param);
        return "toto";
    }
}
