package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.ConfigurationListener;
import com.thoughtworks.webstub.config.HttpConfiguration;

public class HttpServerStub implements ConfigurationListener {
    private final HttpServer server;
    private Configurations configurations;

    public HttpServerStub(HttpServer httpServer) {
        server = httpServer;
        initConfigurations();
    }

    @Override
    public void configurationCreated(HttpConfiguration configuration) {
        configurations.add(configuration);
    }

    public void start() {
        server.start();
    }

    public void reset() {
        initConfigurations();
    }

    public void stop() {
        server.stop();
    }

    private void initConfigurations() {
        configurations = new Configurations();
        server.removeHandlerChain("/");
        server.addHandlerChain("/", new StubServlet(configurations));
    }
}

