package org.pasut.test.microservices.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/")
@EnableAutoConfiguration //como agregue la dependencia web (tomcat + spring MVC), asume que estoy creando una aplicacion web
@RefreshScope
@EnableEurekaClient
@ComponentScan
@EnableFeignClients
public class MainTest {
    private final Random rnd = new Random();

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    ServiceTwoRestTemplateClient serviceTwo;

    @Autowired
    ServiceTwoFeignClient serviceTwoFeign;

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        RestTemplate template = new RestTemplate();
        List interceptors = template.getInterceptors();
        if (interceptors == null) {
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        } else {
            interceptors.add(new UserContextInterceptor()); template.setInterceptors(interceptors);
        }
        return template;
    }

    @RequestMapping(value="/{echo}", method= RequestMethod.GET)
    String echo(@PathVariable String echo) {
        return "Service three says " + echo + " but service two says " + getServiceTwoSaysWithFeign();
    }

    private String getServiceTwoSaysWithDiscoveryService() {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("service-two");

        if (instances.size()==0) return null;
        URI serviceUri = instances.get(rnd.nextInt(instances.size())).getUri();

        ResponseEntity<String> restExchange = restTemplate.exchange(
                serviceUri,
                HttpMethod.GET,
                null, String.class);
        return restExchange.getBody();

    }

    private String getServiceTwoSaysWithRestTemplate() {
        return serviceTwo.say();

    }

    private String getServiceTwoSaysWithFeign() {
        return serviceTwoFeign.say();

    }

    public static void main(String[] args) {
        SpringApplication.run(MainTest.class, args);
    }
}
