package org.pasut.test.microservices.spring.gateway;

import com.netflix.zuul.ZuulFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by boot on 3/12/17.
 */
@Component
public class TrackingFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    @Autowired
    FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isCorrelationIdPresent(){
        return filterUtils.getCorrelationId() != null;
    }

    private String generateCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }

    public Object run() {
        if (isCorrelationIdPresent()) {
            System.out.println(
                String.format("tmx-correlation-id found in tracking filter: %s. ", filterUtils.getCorrelationId()));
        } else{
            filterUtils.setCorrelationId(generateCorrelationId());
            System.out.println(
                    String.format("tmx-correlation-id generated in tracking filter: {}.",
                            filterUtils.getCorrelationId()));
        }
        return null;
    }
}
