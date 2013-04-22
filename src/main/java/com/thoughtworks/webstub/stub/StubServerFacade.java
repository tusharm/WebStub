package com.thoughtworks.webstub.stub;

import com.thoughtworks.webstub.server.HttpServer;
import com.thoughtworks.webstub.server.JettyHttpServer;
import com.thoughtworks.webstub.server.ServletContextHandler;

import static com.thoughtworks.webstub.server.ServletContextFactory.create;

public class StubServerFacade implements HttpServer {
    public static StubServerFacade newServer(int port) {
        return new StubServerFacade(new JettyHttpServer(port));
    }

    private HttpServer httpServer;

    private StubServerFacade(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    public HttpServerStub withContext(String contextRoot) {
        return new HttpServerStub(httpServer, contextRoot);
    }

    @Override
    public void start() {
        httpServer.start();
    }

    @Override
    public void stop() {
        httpServer.stop();
    }

    @Override
    public void addContext(ServletContextHandler contextHandler) {
        throw new UnsupportedOperationException();
    }
}
