package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.stub.creator.ContentCreator;
import com.thoughtworks.webstub.stub.creator.HeadersCreator;
import com.thoughtworks.webstub.stub.creator.ResponsePartCreator;
import com.thoughtworks.webstub.stub.creator.StatusCreator;
import com.thoughtworks.webstub.stub.matcher.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

public class StubServlet extends HttpServlet {
    private HttpConfiguration configuration;
    private final List<ResponsePartCreator> responseCreators;

    public StubServlet(HttpConfiguration configuration) {
        this.configuration = configuration;

        responseCreators = asList(
                new HeadersCreator(configuration),
                new ContentCreator(configuration),
                new StatusCreator(configuration)
        );
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

    HttpConfiguration getConfiguration() {
        return configuration;
    }

    private void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (RequestPartMatcher matcher : asList(new MethodMatcher(request), new UriMatcher(request), new HeadersMatcher(request), new ContentMatcher(request))) {
            if (!matcher.matches(configuration)) {
                response.sendError(matcher.failedResponseCode());
                return;
            }
        }

        for (ResponsePartCreator creator : responseCreators) {
            creator.createFor(response);
        }
    }
}
