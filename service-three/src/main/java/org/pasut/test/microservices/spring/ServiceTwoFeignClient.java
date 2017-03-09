package org.pasut.test.microservices.spring;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by boot on 3/8/17.
 */
@FeignClient("service-two")
public interface ServiceTwoFeignClient {
    @RequestMapping(method= RequestMethod.GET, value="/", consumes="application/json")
    String say();
}
