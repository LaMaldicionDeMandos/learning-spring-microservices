package org.pasut.test.microservices.spring;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by boot on 4/16/17.
 */
public interface CustomChannels {
    @Input("inputCustomChannel")
    SubscribableChannel input();
}
