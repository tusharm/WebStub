package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.ConfigurationListener;
import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.server.HttpServer;
import com.thoughtworks.webstub.server.ServletContextHandler;
import com.thoughtworks.webstub.stub.config.Configurations;

import static com.thoughtworks.webstub.server.ServletContextFactory.create;

public class HttpServerStub implements ConfigurationListener {
    private ServletContextHandler contextHandler;
    private Configurations configurations;

    public HttpServerStub(HttpServer server, String contextRoot) {
        configurations = new Configurations();

        contextHandler = create(contextRoot);
        contextHandler.addServlet("/", new StubServlet(configurations));

        server.addContext(this.contextHandler);
    }

    @Override
    public void configurationCreated(HttpConfiguration configuration) {
        configurations.add(configuration);
    }

    @Override
    public void configurationCleared() {
        configurations.reset();
    }
}

