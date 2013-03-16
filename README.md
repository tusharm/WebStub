# WebStub

This library intends to simplify stubbing out responses from external HTTP entities that your application/service  depends on.
This can be useful in testing your application from within JUnit tests. It internally uses Jetty as the servlet container.

Tests will setup a stub like this:
```java
@BeforeClass
public static void beforeAll() {
    serverStub = dslServer(9099, "/context");
    serverStub.start();
}

@Test
public void shouldStubHttpCalls() {
    serverStub.get("/accounts/1").returns(response().withStatus(200));
    assertThat(httpClient.get("http://localhost:9099/context/accounts/1").status(), is(200));
}

@Before
public void beforeEach() {
    serverStub.reset();
}

@AfterClass
public static void afterAll() {
    serverStub.stop();
}
```
In particular, I think it will be useful in tests which use https://github.com/aharin/inproctester.

Currently, it supports:
+ Stubbing response status for GET, POST, PUT and DELETE

Lots more to come..

## RoadMap

### Immediate:
+ POST, PUT, DELETE
+ request/response body content
+ request/response headers
+ https
+ starting stub servers in-process, rather than on real native ports

## License

WebStub is distributed under the terms of Apache Software License v2.0: http://www.apache.org/licenses/LICENSE-2.0.html
