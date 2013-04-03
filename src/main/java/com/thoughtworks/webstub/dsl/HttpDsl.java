package com.thoughtworks.webstub.dsl;

import com.thoughtworks.webstub.config.*;
import com.thoughtworks.webstub.dsl.builders.EntityEnclosingRequestBuilder;
import com.thoughtworks.webstub.dsl.builders.RequestBuilder;

public class HttpDsl extends ConfigurationProvider {

    public static HttpDsl dslWrapped(ConfigurationListener listener) {
        return new HttpDsl(listener);
    }

    private HttpDsl(ConfigurationListener listener) {
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

}
