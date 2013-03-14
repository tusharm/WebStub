package com.thoughtworks.test.web.stub;

import com.thoughtworks.test.web.config.StubConfiguration;
import com.thoughtworks.test.web.dsl.DslProvider;

public class HttpServerStub extends DslProvider {
    private final HttpServer server;

    public HttpServerStub(HttpServer httpServer) {
        server = httpServer;
    }

    @Override
    public void configurationCreated(StubConfiguration configuration) {
        server.addServlet(new StubServlet(configuration), configuration.uri());
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

