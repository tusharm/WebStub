package com.thoughtworks.test.web.server;

import com.thoughtworks.test.web.HttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

import static org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS;

public class JettyHttpServer implements HttpServer {
    public static final String STATUS_PATH = "/status";

    private Server server;
    private ServletContextHandler context;
    private JettyServletRemover servletRemover;

    public JettyHttpServer(int port, String contextRoot) {
        context = createContext(contextRoot);

        server = new Server(port);
        server.setHandler(context);

        servletRemover = new JettyServletRemover(context);

        addServlet(new StatusServlet(200), STATUS_PATH);
    }

    @Override
    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Unable to start server", e);
        }
    }

    @Override
    public void addServlet(Servlet servlet, String servletPath) {
        context.addServlet(new ServletHolder(servlet), servletPath);
    }

    @Override
    public void removeServlet(String servletPath) {
        servletRemover.remove(servletPath);
    }

    @Override
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
