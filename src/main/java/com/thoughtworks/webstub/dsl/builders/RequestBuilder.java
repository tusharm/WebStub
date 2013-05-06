package com.thoughtworks.webstub.dsl.builders;

import com.thoughtworks.webstub.config.ConfigurationProvider;
import com.thoughtworks.webstub.config.HttpConfiguration;
import com.thoughtworks.webstub.config.Request;
import com.thoughtworks.webstub.config.Header;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class RequestBuilder<T extends RequestBuilder<T>> {
    private ConfigurationProvider configurationProvider;
    protected String uri;
    protected String method;
    protected Collection<Header> headers = new ArrayList<Header>();

    public RequestBuilder(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }

    public T withMethod(String method) {
        this.method = method;
        return (T) this;
    }

    public T withUri(String uri) {
        this.uri = uri;
        return (T) this;
    }

    public T withHeader(String name, String value) {
        headers.add(new Header(name, value));
        return (T) this;
    }

    public T withHeaders(Map<String, String> headersMap) {
        headers.clear();
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            headers.add(new Header(entry.getKey(), entry.getValue()));
        }
        return (T) this;
    }

    protected Request build() {
        return new Request(method, uri, null, headers);
    }

    public final void returns(ResponseBuilder responseBuilder) {
        configurationProvider.configurationCreated(new HttpConfiguration(this.build(), responseBuilder.build()));
    }
}
