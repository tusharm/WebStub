package com.thoughtworks.webstub;

import com.thoughtworks.webstub.dsl.HttpDsl;
import com.thoughtworks.webstub.stub.HttpServerStub;
import com.thoughtworks.webstub.utils.Client;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.thoughtworks.webstub.StubServerFactory.stubServer;
import static com.thoughtworks.webstub.dsl.HttpDsl.dslWrapped;
import static com.thoughtworks.webstub.dsl.ResponseBuilder.response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SomeTest {
    private static HttpServerStub stub;
    private static HttpDsl dslServer;
    private Client httpClient = new Client();

    @BeforeClass
    public static void beforeAll() {
        stub = stubServer(9099, "/context");
        dslServer = dslWrapped(stub);
        stub.start();
    }

    @Test
    public void shouldStubHttpCalls() {
        dslServer.get("/accounts/1").returns(response().withStatus(200));
        assertThat(httpClient.get("http://localhost:9099/context/accounts/1").status(), is(200));
    }

    @After
    public void afterEach() {
        stub.reset();
    }

    @AfterClass
    public static void afterAll() {
        stub.stop();
    }
}
