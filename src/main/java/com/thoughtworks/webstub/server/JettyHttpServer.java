package com.thoughtworks.webstub.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.util.EnumSet;

import static com.thoughtworks.webstub.server.ServletContextFactory.STATUS_PATH;

public class JettyHttpServer implements HttpServer {

    private Server server;
    private ServletContextHandler context;

    public JettyHttpServer(int port, ServletContextHandler context) {
        this.context = context;

        server = new Server(port);
        server.setHandler(context);
    }

    @Override
    public void addHandlerChain(String contextRelativePath, HttpServlet servlet) {
        context.addServlet(contextRelativePath, servlet);
    }

    @Override
    public void removeHandlerChain(String contextRelativePath) {
        context.removeServlet(contextRelativePath);
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
}
