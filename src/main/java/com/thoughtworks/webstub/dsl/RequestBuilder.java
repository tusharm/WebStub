package com.thoughtworks.webstub.dsl;

import com.thoughtworks.webstub.config.ConfigurationProvider;
import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.config.Request;

public class RequestBuilder {
    private ConfigurationProvider configurationProvider;
    private String content;
    private String uri;
    private String method;

    RequestBuilder(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }

    RequestBuilder withMethod(String method) {
        this.method = method;
        return this;
    }

    RequestBuilder withUri(String uri) {
        this.uri = uri;
        return this;
    }

    public RequestBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public final void returns(ResponseBuilder responseBuilder) {
        Request request = new Request(method, uri, content);
        configurationProvider.configurationCreated(new HttpConfiguration(request, responseBuilder.build()));
    }
}
