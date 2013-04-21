package com.thoughtworks.webstub.server;

public interface HttpServer {
    void start();
    void stop();
    void addContext(ServletContextHandler contextHandler);
}
