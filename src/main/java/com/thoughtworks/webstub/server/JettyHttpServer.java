package com.thoughtworks.webstub.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class JettyHttpServer implements HttpServer {

    private Server server;
    private ContextHandlerCollection handlerCollection;

    public JettyHttpServer(int port) {
        handlerCollection = new ContextHandlerCollection();

        server = new Server(port);
        server.setHandler(handlerCollection);
    }

    @Override
    public void addContext(ContextHandler contextHandler) {
        handlerCollection.addHandler(contextHandler);
        start(contextHandler);
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
