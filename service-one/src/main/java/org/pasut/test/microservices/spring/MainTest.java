package org.pasut.test.microservices.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.applet.Main;

@RestController
@RequestMapping("/")
@ComponentScan
@EnableAutoConfiguration //como agregue la dependencia web (tomcat + spring MVC), asume que estoy creando una aplicacion web
@RefreshScope
@EnableBinding(Source.class)
@EnableEurekaClient
@EnableHystrix
public class MainTest {
    private final static Logger log = LoggerFactory.getLogger(MainTest.class);

    @Autowired
    EchoSourceBean echoSender;

    @RequestMapping(value="/echo/{echo}", method= RequestMethod.GET)
    String echo(@Value("${property}") String hello, @PathVariable String echo) {
        echoSender.send(echo);
        return hello + " says " + echo;
    }

    @RequestMapping("/")
    String hello(@Value("${property}") String hello) {
        log.info("Call to hello: " + hello);
        return hello;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainTest.class, args);
    }
}
