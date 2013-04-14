package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.server.JettyHttpServer;
import com.thoughtworks.webstub.server.HttpServer;
import com.thoughtworks.webstub.server.ServletContextFactory;

import static com.thoughtworks.webstub.server.ServletContextFactory.create;

public class StubServerFactory {
    public static HttpServerStub stubServer(int port, String contextRoot) {
        HttpServer httpServer = new JettyHttpServer(port, create(contextRoot));
        return new HttpServerStub(httpServer);
    }
}
