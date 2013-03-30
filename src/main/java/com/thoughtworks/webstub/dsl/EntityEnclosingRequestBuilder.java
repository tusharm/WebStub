package com.thoughtworks.webstub.dsl;

import com.thoughtworks.webstub.config.ConfigurationProvider;
import com.thoughtworks.webstub.config.Request;

public class EntityEnclosingRequestBuilder extends RequestBuilder {
    private String content;

    EntityEnclosingRequestBuilder(ConfigurationProvider configurationProvider) {
        super(configurationProvider);
    }

    public EntityEnclosingRequestBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    protected Request build() {
        return new Request(method, uri, content, headers);
    }
}
