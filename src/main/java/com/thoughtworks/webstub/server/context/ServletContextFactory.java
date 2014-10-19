package com.thoughtworks.webstub.server.context;

import com.thoughtworks.webstub.server.servlet.StatusServlet;

import static org.apache.commons.lang.StringUtils.isBlank;

public class ServletContextFactory {
    public static final String STATUS_PATH = "/__status__";

    public static ServletContextHandler create(String contextRoot) {
        ServletContextHandler context = contextFor(contextRoot);
        context.addServlet(STATUS_PATH, new StatusServlet(200));
        return context;
    }

    private static ServletContextHandler contextFor(String contextRoot) {
        if (isBlank(contextRoot))
            throw new IllegalArgumentException("Invalid context root");

        String prefixedRoot = contextRoot.startsWith("/") ? contextRoot : ("/" + contextRoot);
        return new ServletContextHandler(prefixedRoot);
    }
}
