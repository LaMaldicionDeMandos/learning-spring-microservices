package org.pasut.test.microservices.spring;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;
import java.util.Random;

@RestController
@RequestMapping("/")
@EnableAutoConfiguration //como agregue la dependencia web (tomcat + spring MVC), asume que estoy creando una aplicacion web
@RefreshScope
@EnableEurekaClient
@EnableHystrix
@Configuration
@EnableResourceServer
public class MainTest {
    private final static Random rnd = new Random();

    @RequestMapping("/")
    @HystrixCommand(fallbackMethod = "errorHandler")
    String hello(@Value("${instance}") String hello) {
        try {
            Thread.sleep(rnd.nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hello;
    }

    String errorHandler(@Value("${instance}") String hello) {
        return "say " + hello + " but with error by timeout";
    }

    public static void main(String[] args) {
        SpringApplication.run(MainTest.class, args);
    }
}
