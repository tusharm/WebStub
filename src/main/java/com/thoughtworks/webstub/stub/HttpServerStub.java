package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.ConfigurationListener;
import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.server.HttpServer;
import com.thoughtworks.webstub.server.ServletContextHandler;
import com.thoughtworks.webstub.stub.config.Configurations;

public class HttpServerStub implements ConfigurationListener {
    private HttpServer server;
    private ServletContextHandler contextHandler;
    private Configurations configurations;

    public HttpServerStub(HttpServer httpServer, ServletContextHandler contextHandler) {
        server = httpServer;
        this.contextHandler = contextHandler;
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
        contextHandler.removeServlet("/");
        contextHandler.addServlet("/", new StubServlet(configurations));
    }
}

