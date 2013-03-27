package com.thoughtworks.webstub.stub;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

public interface HttpServer {
    void start();
    void stop();
    void addHandlerChain(String pathSpec, HttpServlet servlet, Filter... filters);
    void removeHandlerChain(String pathSpec);
}
