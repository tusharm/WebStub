package com.thoughtworks.webstub.server.context;

import com.thoughtworks.webstub.config.ConfigurationListener;
import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.server.HttpServer;
import com.thoughtworks.webstub.server.servlet.ConfigurableServlet;
import com.thoughtworks.webstub.server.servlet.Configurations;
import com.thoughtworks.webstub.server.servlet.StatusServlet;

import static org.apache.commons.lang.StringUtils.isBlank;

public class ConfigurableContext implements ConfigurationListener {
    private Configurations configurations;

    public ConfigurableContext(HttpServer server, String contextRoot) {
        assertNotBlank(contextRoot);

        ServletContextHandler context = new ServletContextHandler(sanitized(contextRoot));
        context.addServlet("/__status__", new StatusServlet(200));

        configurations = new Configurations();
        context.addServlet("/", new ConfigurableServlet(configurations));

        server.addContext(context);
    }

    @Override
    public void configurationCreated(HttpConfiguration configuration) {
        configurations.add(configuration);
    }

    @Override
    public void configurationCleared() {
        configurations.reset();
    }

    private void assertNotBlank(String contextRoot) {
        if (isBlank(contextRoot))
            throw new IllegalArgumentException("Invalid context root");
    }

    private String sanitized(String contextRoot) {
        return contextRoot.startsWith("/") ? contextRoot : ("/" + contextRoot);
    }
}

