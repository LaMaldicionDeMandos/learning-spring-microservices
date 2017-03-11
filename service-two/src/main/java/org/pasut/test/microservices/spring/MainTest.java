package org.pasut.test.microservices.spring;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/")
@EnableAutoConfiguration //como agregue la dependencia web (tomcat + spring MVC), asume que estoy creando una aplicacion web
@RefreshScope
@EnableEurekaClient
@EnableHystrix
@Configuration
public class MainTest {
    private final static Random rnd = new Random();

    @RequestMapping("/")
    @HystrixCommand(fallbackMethod = "errorHandler", //El metodo que se ejecuta si sale por timeout,
            threadPoolKey = "helloThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"), // Cantidad de threads
                    @HystrixProperty(name = "maxQueueSize", value = "10"), // Tamaño de la cola de espera
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    }
                    )
    String hello(@Value("${instance}") String hello) {
        try {
            Thread.sleep(rnd.nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hello;
    }

    String errorHandler(String hello) {
        return "say " + hello + " but with error by timeout";
    }

    public static void main(String[] args) {
        SpringApplication.run(MainTest.class, args);
    }
}
