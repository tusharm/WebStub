package com.thoughtworks.webstub;

import com.thoughtworks.webstub.dsl.HttpDsl;
import com.thoughtworks.webstub.utils.Client;
import com.thoughtworks.webstub.utils.Response;
import org.apache.http.message.BasicHeader;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.thoughtworks.webstub.StubServerFacade.newServer;
import static com.thoughtworks.webstub.dsl.builders.ResponseBuilder.response;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HttpStubTest {
    private static StubServerFacade server;
    private static HttpDsl stubServer;
    private Client httpClient = new Client();

    @BeforeClass
    public static void beforeAll() {
        server = newServer(9099);
        stubServer = server.withContext("/context");

        server.start();
    }

    @Before
    public void setUp() {
        stubServer.reset();
    }

    @Test
    public void shouldStubHttpCalls() {
        stubServer.get("/accounts/1").returns(response(200).withContent("account details"));

        Response response = httpClient.get("http://localhost:9099/context/accounts/1");
        assertThat(response.status(), is(200));
        assertThat(response.content(), is("account details"));
    }

    @Test
    public void shouldMatchResponseForRequestsContainingHeaders() {
        stubServer.get("/accounts/1").withHeader("x", "y").returns(response(200));

        Response response = httpClient.get("http://localhost:9099/context/accounts/1", asList(new BasicHeader("x", "y")));
        assertThat(response.status(), is(200));
    }

    @AfterClass
    public static void afterAll() {
        server.stop();
    }
}
