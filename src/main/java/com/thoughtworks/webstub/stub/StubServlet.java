package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.stub.config.Configurations;
import com.thoughtworks.webstub.stub.config.MissingMatchingConfigurationException;
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
    private Configurations configurations;

    public StubServlet(Configurations configurations) {
        this.configurations = configurations;
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

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Configurations filtered = configurations
                    .filterBy(new MethodMatcher(request))
                    .filterBy(new UriMatcher(request))
                    .filterBy(new QueryStringMatcher(request))
                    .filterBy(new HeadersMatcher(request))
                    .filterBy(new ContentMatcher(request));

            for (ResponsePartCreator creator : responseCreators(filtered.last())) {
                creator.createFor(response);
            }
        } catch (MissingMatchingConfigurationException e) {
            response.setStatus(e.getFailedMatcher().failedResponseCode());
        }
    }

    private List<ResponsePartCreator> responseCreators(HttpConfiguration configuration) {
        return asList(
                new HeadersCreator(configuration),
                new ContentCreator(configuration),
                new StatusCreator(configuration)
        );
    }
}
