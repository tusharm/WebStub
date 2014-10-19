package com.thoughtworks.webstub.server.servlet;

import com.thoughtworks.webstub.server.utils.JettyHandlerRemover;
import com.thoughtworks.webstub.server.utils.JettyServletRemover;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;

public class ServletContextHandler extends org.eclipse.jetty.servlet.ServletContextHandler {
    private JettyHandlerRemover servletRemover;

    public ServletContextHandler(String contextRoot) {
        super(null, contextRoot, org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS);
        this.servletRemover = new JettyServletRemover(this);
    }

    public void addServlet(String contextRelativePath, HttpServlet servlet) {
        addServlet(new ServletHolder(servlet), contextRelativePath);
    }

    public void removeServlet(String contextRelativePath) {
        servletRemover.remove(contextRelativePath);
    }
}
