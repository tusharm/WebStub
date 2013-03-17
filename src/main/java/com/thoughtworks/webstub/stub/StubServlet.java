package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_NOT_IMPLEMENTED;

public class StubServlet extends HttpServlet {
    private HttpConfiguration configuration;

    public StubServlet(HttpConfiguration configuration) {
        this.configuration = configuration;
    }

    public HttpConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    @Override
    public final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(!configuration.method().equals(req.getMethod())) {
            resp.sendError(SC_NOT_IMPLEMENTED);
            return;
        }

        if(!configuration.uri().equals(req.getServletPath())) {
            resp.sendError(SC_NOT_FOUND);
            return;
        }

        resp.setStatus(configuration.status());
    }
}
