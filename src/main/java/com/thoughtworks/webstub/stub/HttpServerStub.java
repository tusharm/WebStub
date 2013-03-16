package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.StubConfiguration;
import com.thoughtworks.webstub.dsl.DslProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.unmodifiableCollection;

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

    Collection<String> registeredUris() {
        return unmodifiableCollection(registeredUris);
    }
}

