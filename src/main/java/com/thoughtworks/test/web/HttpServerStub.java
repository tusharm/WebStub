package com.thoughtworks.test.web;

import com.thoughtworks.test.web.dsl.StubDsl;
import com.thoughtworks.test.web.dsl.Operation;
import com.thoughtworks.test.web.server.HttpServer;

public class HttpServerStub extends HttpServer implements StubDsl {

    public HttpServerStub(int port, String contextRoot) {
        super(port, contextRoot);
    }

    @Override
    public Operation get(String uri) {
        return null;
    }

    public void reset() {

    }
}

