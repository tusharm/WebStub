package com.thoughtworks.webstub.dsl;

import com.thoughtworks.webstub.config.*;

public class HttpDsl extends ConfigurationProvider {

    public static HttpDsl dslWrapped(ConfigurationListener listener) {
        return new HttpDsl(listener);
    }

    private HttpDsl(ConfigurationListener listener) {
        super(listener);
    }

    public RequestBuilder get(String uri) {
        return requestBuilder("GET", uri);
    }

    public RequestBuilder post(String uri) {
        return requestBuilder("POST", uri);
    }

    public RequestBuilder put(String uri) {
        return requestBuilder("PUT", uri);
    }

    public RequestBuilder delete(String uri) {
        return requestBuilder("DELETE", uri);
    }

    private RequestBuilder requestBuilder(String method, String uri) {
        return new RequestBuilder(this).withMethod(method).withUri(uri);
    }
}
