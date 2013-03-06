package com.thoughtworks.test.web.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_OK;

class TestServlet extends HttpServlet {
    private int statusCode;

    TestServlet(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(statusCode);
        resp.setContentType("text/plain");
        resp.getWriter().append("Server is running");
    }
}
