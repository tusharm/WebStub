package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.config.ConfigurationListener;
import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.stub.matcher.ConfigurationMatcher;
import com.thoughtworks.webstub.stub.matcher.ContentMatcher;
import com.thoughtworks.webstub.stub.matcher.MethodMatcher;
import com.thoughtworks.webstub.stub.matcher.UriMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.lastIndexOfSubList;
import static java.util.Collections.unmodifiableCollection;

public class HttpServerStub implements ConfigurationListener {
    private final HttpServer server;
    private Collection<String> registeredUris = new ArrayList<String>();

    public HttpServerStub(HttpServer httpServer) {
        server = httpServer;
    }

    @Override
    public void configurationCreated(HttpConfiguration configuration) {
        server.addHandlerChain(
                configuration.uri(),
                new StubServlet(configuration),
                new StubFilter(createConfigurationMatchers(configuration))
        );
        registeredUris.add(configuration.uri());
    }

    public void start() {
        server.start();
    }

    public void reset() {
        for (String uri : registeredUris) {
            server.removeHandlerChain(uri);
        }
        registeredUris.clear();
    }

    public void stop() {
        server.stop();
    }

    Collection<String> registeredUris() {
        return unmodifiableCollection(registeredUris);
    }

    private List<? extends ConfigurationMatcher> createConfigurationMatchers(HttpConfiguration configuration) {
        return asList(new MethodMatcher(configuration), new UriMatcher(configuration), new ContentMatcher(configuration));
    }
}

