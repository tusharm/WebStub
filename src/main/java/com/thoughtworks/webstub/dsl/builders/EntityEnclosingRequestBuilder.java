package com.thoughtworks.webstub.dsl.builders;

import com.thoughtworks.webstub.config.ConfigurationProvider;
import com.thoughtworks.webstub.config.Request;

public class EntityEnclosingRequestBuilder extends RequestBuilder {
    private String content;

    public EntityEnclosingRequestBuilder(ConfigurationProvider configurationProvider) {
        super(configurationProvider);
    }

    public EntityEnclosingRequestBuilder withContent(String content) {
        return withContent(new StringContentBuilder(content));
    }

    public EntityEnclosingRequestBuilder withContent(ContentBuilder contentBuilder) {
        this.content = contentBuilder.build();
        return this;
    }

    @Override
    protected Request build() {
        return new Request(method, uri, content, headers);
    }
}
