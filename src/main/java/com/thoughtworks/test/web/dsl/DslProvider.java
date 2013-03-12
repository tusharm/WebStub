package com.thoughtworks.test.web.dsl;

import com.thoughtworks.test.web.config.StubConfiguration;

public abstract class DslProvider {
    private Request request;

    public DslProvider get(String uri) {
        request = new Request("GET", uri);
        return this;
    }

    public void returns(Response response) {
        configurationCreated(
                new StubConfiguration(request.method(), request.uri(), response.status()));
    }

    abstract protected void configurationCreated(StubConfiguration configuration);
}
