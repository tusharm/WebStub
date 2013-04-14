package com.thoughtworks.webstub.server;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

public interface HttpServer {
    void start();
    void stop();
    void addHandlerChain(String pathSpec, HttpServlet servlet);
    void removeHandlerChain(String pathSpec);
}
