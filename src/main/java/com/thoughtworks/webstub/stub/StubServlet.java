package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.stub.matcher.ConfigurationMatcher;
import com.thoughtworks.webstub.stub.matcher.ContentMatcher;
import com.thoughtworks.webstub.stub.matcher.MethodMatcher;
import com.thoughtworks.webstub.stub.matcher.UriMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

public class StubServlet extends HttpServlet {
    private final List<? extends ConfigurationMatcher> matchers;
    private HttpConfiguration configuration;

    public StubServlet(HttpConfiguration configuration) {
        this.configuration = configuration;
        matchers = asList(
                new MethodMatcher(configuration),
                new UriMatcher(configuration),
                new ContentMatcher(configuration)
        );
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

    private void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (ConfigurationMatcher matcher : matchers) {
            if (!matcher.matches(request)) {
                response.sendError(matcher.failedResponseCode());
                return;
            }
        }

        if (configuration.responseContent() != null) {
            response.getWriter().print(configuration.responseContent());
        }

        response.setStatus(configuration.status());
    }
}
