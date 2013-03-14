package com.thoughtworks.test.web.stub;

import javax.servlet.Servlet;

public interface HttpServer {
    void start();
    void stop();
    void addServlet(Servlet servlet, String servletPath);
    void removeServlet(String servletPath);
}
