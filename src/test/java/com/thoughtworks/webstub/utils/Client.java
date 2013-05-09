package com.thoughtworks.webstub.utils;

import org.apache.http.Header;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.omg.PortableInterceptor.TRANSPORT_RETRY;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

public class Client {
    private DefaultHttpClient httpClient;

    public Client() {
        httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 1000);
    }

    public Response get(String url) {
        return execute(new HttpGet(url));
    }

    public Response getWithParams(String url) {
        try {
            String[] splitted = url.split("\\?");
            URI uri = new URIBuilder(splitted[0]).setQuery(splitted[1]).build();
            return execute(new HttpGet(uri));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response get(String url, List<? extends Header> headers) {
        HttpGet request = new HttpGet(url);
        request.setHeaders(headers.toArray(new Header[]{}));
        return execute(request);
    }

    public Response post(String url) {
        return execute(new HttpPost(url));
    }

    public Response post(String url, String content) {
        return executeWithContent(new HttpPost(url), content);
    }

    public Response put(String url, String content) {
        return executeWithContent(new HttpPut(url), content);
    }

    public Response delete(String url) {
        return execute(new HttpDelete(url));
    }

    public Response options(String url) {
        return execute(new HttpOptions(url));
    }

    public Response head(String url) {
        return execute(new HttpHead(url));
    }

    public Response trace(String url) {
        return execute(new HttpTrace(url));
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
