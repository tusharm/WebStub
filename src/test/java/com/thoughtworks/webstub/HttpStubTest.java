package com.thoughtworks.webstub;

import com.thoughtworks.webstub.dsl.HttpDsl;
import com.thoughtworks.webstub.stub.HttpServerStub;
import com.thoughtworks.webstub.utils.Client;
import com.thoughtworks.webstub.utils.Response;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static com.thoughtworks.webstub.StubServerFactory.stubServer;
import static com.thoughtworks.webstub.dsl.HttpDsl.dslWrapped;
import static com.thoughtworks.webstub.dsl.builders.ResponseBuilder.response;
import static java.util.Arrays.asList;
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

    @Test
    public void shouldMatchResponseForRequestsContainingHeaders() {
        dslServer.get("/accounts/1").withHeader("x", "y").returns(response(200));

        Response response = httpClient.get("http://localhost:9099/context/accounts/1", asList(new BasicHeader("x", "y")));
        assertThat(response.status(), is(200));
    }

    @Test
    public void shouldTestUriBuilder() throws URISyntaxException, UnsupportedEncodingException {
        URI uri = new URIBuilder("http://localhost:9090/dogs/1").setQuery("color=blue&name=Handsome Jack").build();
        assertThat(uri.getHost(), is("localhost"));
        assertThat(uri.getPort(), is(9090));
        assertThat(URLDecoder.decode(uri.getQuery(), "UTF-8"), is("color=blue&name=Handsome Jack"));
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
