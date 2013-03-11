package com.thoughtworks.test.web.dsl;

public class DslProvider implements Dsl {
    private DslConsumer consumer;

    public DslProvider(DslConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public Request get(String uri) {
        return new Request("GET", uri);
    }
}
