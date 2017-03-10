package org.pasut.test.microservices.spring;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/")
@EnableAutoConfiguration //como agregue la dependencia web (tomcat + spring MVC), asume que estoy creando una aplicacion web
@RefreshScope
@EnableEurekaClient
public class MainTest {
    private final static Random rnd = new Random();

    @RequestMapping("/")
    @HystrixCommand
    String hello(@Value("${instance}") String hello) {
        try {
            Thread.sleep(rnd.nextInt(1500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hello;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainTest.class, args);
    }
}
