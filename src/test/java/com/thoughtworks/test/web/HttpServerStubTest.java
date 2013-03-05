package com.thoughtworks.test.web;

import com.thoughtworks.test.web.utils.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HttpServerStubTest {
    private Client client = new Client();

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowInvalidContextRoot() {
        new HttpServerStub(1234, null);
    }

    @Test
    public void shouldReturnSuccessStatusIfServerStarted() {
        HttpServerStub serverStub = new HttpServerStub(9099, "root");
        serverStub.start();

        assertThat(client.get("http://localhost:9099/root/status").status(), is(200));

        serverStub.stop();
    }

    @Test(expected = RuntimeException.class)
    public void shouldErrorOutIfServerIsNotRunning() {
        client.get("http://localhost:9099/root/status");
    }
}
