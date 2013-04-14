package com.thoughtworks.webstub.server;

import com.thoughtworks.webstub.server.utils.JettyHandlerRemover;
import com.thoughtworks.webstub.server.utils.JettyServletRemover;

public class ServletContextHandler extends org.eclipse.jetty.servlet.ServletContextHandler {
    private JettyHandlerRemover servletRemover;

    public ServletContextHandler(String contextRoot) {
        super(null, contextRoot, org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS);
        this.servletRemover = new JettyServletRemover(this);
    }

    public void removeServlet(String pathSpec) {
        servletRemover.remove(pathSpec);
    }
}
