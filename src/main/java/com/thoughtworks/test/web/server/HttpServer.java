package com.thoughtworks.test.web.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import static org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS;

public class HttpServer {
    private static final String STATUS_PATH = "/status";
    private Server server;

    protected HttpServer(int port, String contextRoot) {
        ServletContextHandler context = createContextWithStatusServlet(contextRoot);

        server = new Server(port);
        server.setHandler(context);
    }

    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Unable to start server", e);
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException("Unable to stop server", e);
        }
    }

    private ServletContextHandler createContextWithStatusServlet(String contextRoot) {
        String validatedRoot = validateAndSanitize(contextRoot);
        ServletContextHandler context = new ServletContextHandler(null, validatedRoot, SESSIONS);

        context.addServlet(new ServletHolder(new StatusServlet()), STATUS_PATH);
        return context;
    }

    private String validateAndSanitize(String contextRoot) {
        if (contextRoot == null)
            throw new IllegalArgumentException("Invalid context root");

        return contextRoot.startsWith("/") ? contextRoot : ("/" + contextRoot);
    }
}
