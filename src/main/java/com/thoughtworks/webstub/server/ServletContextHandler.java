package com.thoughtworks.webstub.server;

import com.thoughtworks.webstub.server.utils.JettyFilterRemover;
import com.thoughtworks.webstub.server.utils.JettyHandlerRemover;
import com.thoughtworks.webstub.server.utils.JettyServletRemover;

public class ServletContextHandler extends org.eclipse.jetty.servlet.ServletContextHandler {
    private JettyHandlerRemover servletRemover;
    private JettyHandlerRemover filterRemover;

    public ServletContextHandler(String contextRoot) {
        super(null, contextRoot, org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS);
        this.servletRemover = new JettyServletRemover(this);
        this.filterRemover = new JettyFilterRemover(this);
    }

    public void removeServlet(String pathSpec) {
        servletRemover.remove(pathSpec);
    }

    public void removeFilter(String pathSpec) {
        filterRemover.remove(pathSpec);
    }
}
