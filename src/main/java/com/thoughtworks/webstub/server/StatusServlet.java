package com.thoughtworks.webstub.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class StatusServlet extends HttpServlet {
    public static final String DEFAULT_MESSAGE = "Server is running";

    private int statusCode;
    private String message;

    StatusServlet(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    StatusServlet(int statusCode) {
        this(statusCode, DEFAULT_MESSAGE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(statusCode);
        resp.setContentType("text/plain");
        resp.getWriter().append(message);
    }
}
