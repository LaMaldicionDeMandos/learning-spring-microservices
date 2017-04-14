package org.pasut.test.microservices.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by boot on 4/14/17.
 */
@Component
public class EchoSourceBean {
    private final Source source;

    private final static Logger logger = LoggerFactory.getLogger(EchoSourceBean.class);

    @Autowired
    public EchoSourceBean(final Source source) {
        this.source = source;
    }

    public void send(final String message) {
        logger.debug("Sending Kafka message {}", message);
        EchoModel echo = new EchoModel(message);
        source.output().send(MessageBuilder.withPayload(echo).build());
    }
}
