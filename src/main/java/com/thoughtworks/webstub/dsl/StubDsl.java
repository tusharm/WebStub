package com.thoughtworks.webstub.dsl;

import com.thoughtworks.webstub.config.*;
import com.thoughtworks.webstub.dsl.builders.EntityEnclosingRequestBuilder;
import com.thoughtworks.webstub.dsl.builders.RequestBuilder;

public class StubDsl extends ConfigurationProvider {

    public StubDsl(ConfigurationListener listener) {
        super(listener);
    }

    public RequestBuilder get(String uri) {
        return new RequestBuilder(this).withMethod("GET").withUri(uri);
    }

    public EntityEnclosingRequestBuilder post(String uri) {
        return new EntityEnclosingRequestBuilder(this).withMethod("POST").withUri(uri);
    }

    public EntityEnclosingRequestBuilder put(String uri) {
        return new EntityEnclosingRequestBuilder(this).withMethod("PUT").withUri(uri);
    }

    public RequestBuilder delete(String uri) {
        return new RequestBuilder(this).withMethod("DELETE").withUri(uri);
    }

    public EntityEnclosingRequestBuilder options(String uri) {
        return new EntityEnclosingRequestBuilder(this).withMethod("OPTIONS").withUri(uri);
    }

    public RequestBuilder head(String uri) {
        return new RequestBuilder(this).withMethod("HEAD").withUri(uri);
    }

    public RequestBuilder trace(String uri) {
        return new RequestBuilder(this).withMethod("TRACE").withUri(uri);
    }

    public EntityEnclosingRequestBuilder patch(String uri) {
        return new EntityEnclosingRequestBuilder(this).withMethod("PATCH").withUri(uri);
    }

    public void reset() {
        configurationCleared();
    }
}
