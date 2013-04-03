package com.thoughtworks.webstub.dsl.builders;

import com.thoughtworks.webstub.config.ConfigurationProvider;
import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.config.Request;
import com.thoughtworks.webstub.dsl.Header;

import java.util.ArrayList;
import java.util.Collection;

public class RequestBuilder {
    private ConfigurationProvider configurationProvider;
    protected String uri;
    protected String method;
    protected Collection<Header> headers = new ArrayList<Header>();

    public RequestBuilder(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }

    public <T extends RequestBuilder> T withMethod(String method) {
        this.method = method;
        return (T) this;
    }

    public <T extends RequestBuilder> T withUri(String uri) {
        this.uri = uri;
        return (T) this;
    }

    public <T extends RequestBuilder> T withHeader(String name, String value) {
        headers.add(new Header(name, value));
        return (T) this;
    }

    protected Request build() {
        return new Request(method, uri, null, headers);
    }

    public final void returns(ResponseBuilder responseBuilder) {
        configurationProvider.configurationCreated(new HttpConfiguration(this.build(), responseBuilder.build()));
    }
}
