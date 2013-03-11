package com.thoughtworks.test.web.dsl;

import com.thoughtworks.test.web.config.StubConfiguration;

public class DslProvider {
    private DslConsumer consumer;
    private Request request;

    public DslProvider(DslConsumer consumer) {
        this.consumer = consumer;
    }

    public DslProvider get(String uri) {
        request = new Request("GET", uri);
        return this;
    }

    public void returns(Response response) {
        consumer.configurationCreated(
                new StubConfiguration(request.method(), request.uri(), response.status()));
    }
}
