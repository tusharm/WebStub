package com.thoughtworks.webstub.utils;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

    public Response put(String url, String content) {
        return executeWithContent(new HttpPut(url), content);
    }

    public Response delete(String url) {
        return execute(new HttpDelete(url));
    }

    private Response executeWithContent(HttpEntityEnclosingRequestBase request, String content) {
        try {
            request.setEntity(new StringEntity(content));
            return execute(request);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error setting entity", e);
        }
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
