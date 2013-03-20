package com.thoughtworks.webstub.dsl;

import com.thoughtworks.webstub.config.*;

public class HttpDsl extends ConfigurationProvider {
    private Request request;

    public static HttpDsl dslWrapped(ConfigurationListener listener) {
        return new HttpDsl(listener);
    }

    private HttpDsl(ConfigurationListener listener) {
        super(listener);
    }

    public HttpDsl get(String uri) {
        request = new Request("GET", uri);
        return this;
    }

    public HttpDsl post(String uri) {
        request = new Request("POST", uri);
        return this;
    }

    public HttpDsl put(String uri) {
        request = new Request("PUT", uri);
        return this;
    }

    public HttpDsl delete(String uri) {
        request = new Request("DELETE", uri);
        return this;
    }

    public void returns(ResponseBuilder responseBuilder) {
        configurationCreated(new HttpConfiguration(request, responseBuilder.build()));
    }
}
