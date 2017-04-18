package org.pasut.test.microservices.spring;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by boot on 4/17/17.
 */
@FeignClient("service-one")
public interface ServiceOneFeinClient {
    @RequestMapping(method= RequestMethod.GET, value="/", consumes="application/json")
    String say();
}
