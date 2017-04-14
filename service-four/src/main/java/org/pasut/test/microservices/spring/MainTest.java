package org.pasut.test.microservices.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration //como agregue la dependencia web (tomcat + spring MVC), asume que estoy creando una aplicacion web
@SpringBootApplication
public class MainTest {
    public static void main(String[] args) {
        SpringApplication.run(MainTest.class, args);
    }
}
