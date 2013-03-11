package com.thoughtworks.test.web.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

import static org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS;

public class ConfigurableServer {
    public static final String STATUS_PATH = "/status";

    private Server server;
    private ServletContextHandler context;
    private JettyServletRemover servletRemover;

    protected ConfigurableServer(int port, String contextRoot) {
        context = createContext(contextRoot);

        server = new Server(port);
        server.setHandler(context);

        servletRemover = new JettyServletRemover(context);

        addServlet(new StatusServlet(200), STATUS_PATH);
    }

    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Unable to start server", e);
        }
    }

    public void addServlet(Servlet servlet, String servletPath) {
        context.addServlet(new ServletHolder(servlet), servletPath);
    }

    public void removeServlet(String servletPath) {
        servletRemover.remove(servletPath);
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException("Unable to stop server", e);
        }
    }

    private ServletContextHandler createContext(String contextRoot) {
        if (contextRoot == null)
            throw new IllegalArgumentException("Invalid context root");

        String prefixedRoot = contextRoot.startsWith("/") ? contextRoot : ("/" + contextRoot);
        return new ServletContextHandler(null, prefixedRoot, SESSIONS);
    }
}