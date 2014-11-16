package com.thoughtworks.webstub.server.context;

import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;

public class ServletContextHandler extends org.eclipse.jetty.servlet.ServletContextHandler {
    public ServletContextHandler(String contextRoot) {
        super(null, contextRoot, org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS);
    }

    public void addServlet(String contextRelativePath, HttpServlet servlet) {
        addServlet(new ServletHolder(servlet), contextRelativePath);
    }
}
