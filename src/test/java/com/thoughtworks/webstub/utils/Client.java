package com.thoughtworks.webstub.utils;

import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;

public class Client {
    private DefaultHttpClient httpClient;

    public Client() {
        httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 1000);
    }

    public Response get(String url) {
        return execute(new HttpGet(url));
    }

    public Response post(String url) {
        return execute(new HttpPost(url));
    }

    public Response put(String url) {
        return execute(new HttpPut(url));
    }

    public Response delete(String url) {
        return execute(new HttpDelete(url));
    }

    private Response execute(HttpRequestBase requestBase) {
        try {
            return new Response(httpClient.execute(requestBase));
        } catch (IOException e) {
            throw new RuntimeException("Error executing request: " + requestBase.getMethod(), e);
        }
        finally {
            requestBase.releaseConnection();
        }
    }
}
