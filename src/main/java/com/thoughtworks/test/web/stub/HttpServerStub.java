package com.thoughtworks.test.web.stub;

import com.thoughtworks.test.web.config.StubConfiguration;
import com.thoughtworks.test.web.dsl.DslProvider;

import java.util.ArrayList;
import java.util.Collection;

public class HttpServerStub extends DslProvider {
    private final HttpServer server;
    private Collection<String> registeredUris = new ArrayList<String>();

    public HttpServerStub(HttpServer httpServer) {
        server = httpServer;
    }

    @Override
    public void configurationCreated(StubConfiguration configuration) {
        server.addServlet(new StubServlet(configuration), configuration.uri());
        registeredUris.add(configuration.uri());
    }

    public void start() {
        server.start();
    }

    public void reset() {
        for (String uri : registeredUris) {
            server.removeServlet(uri);
        }
        registeredUris.clear();
    }

    public void stop() {
        server.stop();
    }
}

