package com.thoughtworks.webstub.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class JettyHttpServer implements HttpServer {

    private Server server;
    private ContextHandlerCollection handlerCollection;

    public JettyHttpServer(int port) {
        server = new Server(port);

        handlerCollection = new ContextHandlerCollection();
        server.setHandler(handlerCollection);
    }

    public JettyHttpServer() {
        // random port
        this(0);
    }

    @Override
    public void addContext(ContextHandler contextHandler) {
        handlerCollection.addHandler(contextHandler);
        start(contextHandler);
    }

    @Override
    public int port() {
        Connector[] connectors = server.getConnectors();
        for (Connector connector : connectors) {
            if (connector instanceof ServerConnector)
                return ((ServerConnector) connector).getLocalPort();
        }

        throw new IllegalStateException("Couldn't find a server connector; this is absurd!");
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

    private void start(ContextHandler contextHandler) {
        try {
            contextHandler.start();
        } catch (Exception e) {
            throw new RuntimeException("Error starting context: " + contextHandler.getDisplayName(), e);
        }
    }
}
