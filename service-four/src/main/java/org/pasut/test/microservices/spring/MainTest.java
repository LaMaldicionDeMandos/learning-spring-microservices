package org.pasut.test.microservices.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableAutoConfiguration //como agregue la dependencia web (tomcat + spring MVC), asume que estoy creando una aplicacion web
@SpringBootApplication
@EnableBinding(Sink.class) //Escucha en el channel definido en la interface Sink, el channel que define sink es "input"
@EnableEurekaClient
public class MainTest {
    private final static Logger logger = LoggerFactory.getLogger(MainTest.class);

    public static void main(String[] args) {
        SpringApplication.run(MainTest.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void loggerSink(final EchoModel model) {
        logger.debug("Receiving echo message {}.", model.getMessage());
        System.out.println("Receiving echo message " + model.getMessage() + ".");
    }
}
