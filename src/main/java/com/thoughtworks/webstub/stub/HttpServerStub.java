package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.ConfigurationListener;
import com.thoughtworks.webstub.config.HttpConfiguration;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Collections.unmodifiableCollection;

public class HttpServerStub implements ConfigurationListener {
    private final HttpServer server;
    private Collection<String> registeredUris = new ArrayList<String>();

    public HttpServerStub(HttpServer httpServer) {
        server = httpServer;
    }

    @Override
    public void configurationCreated(HttpConfiguration configuration) {
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

