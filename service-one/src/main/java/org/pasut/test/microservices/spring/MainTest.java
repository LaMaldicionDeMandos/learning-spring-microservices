package org.pasut.test.microservices.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@EnableAutoConfiguration //como agregue la dependencia web (tomcat + spring MVC), asume que estoy creando una aplicacion web
public class MainTest {

    @RequestMapping(value="/echo/{echo}", method= RequestMethod.GET)
    String echo(@PathVariable String echo) {
        return "Service one hardcoded says " + echo;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainTest.class, args);
    }
}
