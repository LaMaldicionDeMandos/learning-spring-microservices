package org.pasut.test.microservices.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by boot on 3/8/17.
 */
@Component
public class ServiceTwoRestTemplateClient {
    @Autowired
    OAuth2RestTemplate restTemplate;

    public String say() {
        ResponseEntity<String> restExchange =
            restTemplate.exchange("http://service-two/", HttpMethod.GET, null, String.class);
        return restExchange.getBody();
    }
}
