package com.thoughtworks.webstub.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.util.EnumSet;

public class JettyHttpServer implements HttpServer {
    public static final String STATUS_PATH = "/status";

    private Server server;
    private ServletContextHandler context;

    public JettyHttpServer(int port, String contextRoot) {
        context = createContext(contextRoot);
        addHandlerChain(STATUS_PATH, new StatusServlet(200));

        server = new Server(port);
        server.setHandler(context);
    }

    @Override
    public void addHandlerChain(String pathSpec, HttpServlet servlet, Filter... filters) {
        for (Filter filter : filters) {
            context.addFilter(new FilterHolder(filter), pathSpec, EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        }
        context.addServlet(new ServletHolder(servlet), pathSpec);
    }

    @Override
    public void removeHandlerChain(String pathSpec) {
        context.removeFilter(pathSpec);
        context.removeServlet(pathSpec);
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
        return new ServletContextHandler(prefixedRoot);
    }
}
