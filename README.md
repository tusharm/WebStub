# WebStub

This library intends to simplify stubbing out responses from external HTTP entities that your application/service  depends on.
This can be useful in testing your application from within JUnit tests. It internally uses Jetty as the servlet container.

Tests will setup a stub like this:
```java
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
```
Refer to tests in src/test/scala/com/thoughtworks/webstub/stub/HttpServerStubIntegrationSpec.scala.

In particular, I think it will be useful in tests which use https://github.com/aharin/inproctester.
Currently, it's in a basic state; lots more to come...

## RoadMap

### Immediate:
- [x] Stubbing GET, POST, PUT, DELETE response status
- [] request/response body content
- [] request/response headers
- [] https
- [] better test failure messages
- [] starting stub servers in-process, rather than on real native ports

## License

WebStub is distributed under the terms of Apache Software License v2.0: http://www.apache.org/licenses/LICENSE-2.0.html
