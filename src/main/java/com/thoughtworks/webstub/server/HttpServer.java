package com.thoughtworks.webstub.server;

import org.eclipse.jetty.server.handler.ContextHandler;

public interface HttpServer {
    void start();
    void stop();
    void addContext(ContextHandler contextHandler);
}
