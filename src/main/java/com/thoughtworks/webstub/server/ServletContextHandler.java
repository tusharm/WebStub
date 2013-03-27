package com.thoughtworks.webstub.server;

import com.thoughtworks.webstub.server.utils.JettyHandlerRemover;

public class ServletContextHandler extends org.eclipse.jetty.servlet.ServletContextHandler {
    private JettyHandlerRemover servletRemover;

    public ServletContextHandler(JettyHandlerRemover servletRemover, String contextRoot) {
        super(null, contextRoot, org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS);
        this.servletRemover = servletRemover;
    }

    public void removeServlet(String pathSpec) {
        servletRemover.remove(pathSpec);
    }
}
