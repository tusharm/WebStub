package com.thoughtworks.webstub;

import com.thoughtworks.webstub.server.JettyHttpServer;
import com.thoughtworks.webstub.stub.HttpServerStub;
import com.thoughtworks.webstub.stub.HttpServer;

public class StubServerFactory {
    public static HttpServerStub stubServer(int port, String contextRoot) {
        HttpServer httpServer = new JettyHttpServer(port, contextRoot);
        return new HttpServerStub(httpServer);
    }
}
