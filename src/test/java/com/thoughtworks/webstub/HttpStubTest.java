package com.thoughtworks.webstub;

import com.thoughtworks.webstub.dsl.HttpDsl;
import com.thoughtworks.webstub.stub.HttpServerStub;
import com.thoughtworks.webstub.utils.Client;
import com.thoughtworks.webstub.utils.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.thoughtworks.webstub.StubServerFactory.stubServer;
import static com.thoughtworks.webstub.dsl.HttpDsl.dslWrapped;
import static com.thoughtworks.webstub.dsl.ResponseBuilder.response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HttpStubTest {
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
        dslServer.get("/accounts/1").returns(response(200).withContent("account details"));

        Response response = httpClient.get("http://localhost:9099/context/accounts/1");
        assertThat(response.status(), is(200));
        assertThat(response.content(), is("account details"));
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
