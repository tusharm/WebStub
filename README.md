# WebStub [![Build Status](https://travis-ci.org/tusharm/WebStub.png)](https://travis-ci.org/tusharm/WebStub)

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
Refer to tests in [HttpServerStubFunctionalSpec](/src/test/scala/com/thoughtworks/webstub/stub/HttpServerStubFunctionalSpec.scala).

In particular, I think it will be useful in tests which use [inproctester](https://github.com/aharin/inproctester).

## Features

- Stubs a real server (embedded Jetty) on-the-fly from within your tests (so test data setup lies in the test) using a fluent DSL
- Supports GET, POST, PUT and DELETE verbs
- Supports stubbing requests (method, uri, headers, content) and responses (status code, headers, content)
- Allows resetting stub configuration before/after every test, to keep individual tests independent
- Can be used for functional testing of services running externally or inside the test itself (check out [inproctester](https://github.com/aharin/inproctester))

## RoadMap

[Todo](https://trello.com/board/webstub/515729e7ec87d5886b001af3)

## License

WebStub is distributed under the terms of Apache Software License v2.0: http://www.apache.org/licenses/LICENSE-2.0.html
