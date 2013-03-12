package com.thoughtworks.test.web;

import com.thoughtworks.test.web.config.StubConfiguration;
import com.thoughtworks.test.web.dsl.DslProvider;
import com.thoughtworks.test.web.server.JettyHttpServer;

public class HttpServerStub extends DslProvider {
    private final HttpServer server;

    public HttpServerStub(int port, String contextRoot) {
        server = new JettyHttpServer(port, contextRoot);
    }

    @Override
    public void configurationCreated(StubConfiguration configuration) {
    }

    public void start() {
        server.start();
    }

    public void reset() {
    }

    public void stop() {
        server.stop();
    }
}

