package com.thoughtworks.test.web;

import com.thoughtworks.test.web.config.StubConfiguration;
import com.thoughtworks.test.web.dsl.DslClient;
import com.thoughtworks.test.web.server.ConfigurableServer;

public class HttpServerStub extends ConfigurableServer implements DslClient {

    public HttpServerStub(int port, String contextRoot) {
        super(port, contextRoot);
    }

    public void reset() {
    }

    @Override
    public void configurationCreated(StubConfiguration configuration) {

    }
}

