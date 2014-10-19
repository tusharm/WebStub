package com.thoughtworks.webstub.context;

import com.thoughtworks.webstub.config.ConfigurationListener;
import com.thoughtworks.webstub.config.Configurations;
import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.server.servlet.ConfigurableServlet;
import com.thoughtworks.webstub.server.HttpServer;
import com.thoughtworks.webstub.server.servlet.ServletContextHandler;

import static com.thoughtworks.webstub.server.servlet.ServletContextFactory.create;

public class ConfigurableContext implements ConfigurationListener {
    private ServletContextHandler contextHandler;
    private Configurations configurations;

    public ConfigurableContext(HttpServer server, String contextRoot) {
        configurations = new Configurations();

        contextHandler = create(contextRoot);
        contextHandler.addServlet("/", new ConfigurableServlet(configurations));

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

