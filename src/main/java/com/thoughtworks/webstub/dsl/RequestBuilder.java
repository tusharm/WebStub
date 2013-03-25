package com.thoughtworks.webstub.dsl;

import com.thoughtworks.webstub.config.ConfigurationProvider;
import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.config.Request;

public class RequestBuilder {
    private ConfigurationProvider configurationProvider;
    protected String uri;
    protected String method;

    RequestBuilder(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }

    <T extends RequestBuilder> T withMethod(String method) {
        this.method = method;
        return (T) this;
    }

    <T extends RequestBuilder> T withUri(String uri) {
        this.uri = uri;
        return (T) this;
    }

    protected Request build() {
        return new Request(method, uri);
    }

    public final void returns(ResponseBuilder responseBuilder) {
        configurationProvider.configurationCreated(new HttpConfiguration(this.build(), responseBuilder.build()));
    }
}
