# WebStub [![Build Status](https://travis-ci.org/tusharm/WebStub.png)](https://travis-ci.org/tusharm/WebStub)

This library intends to simplify stubbing out responses from external HTTP entities that your application/service  depends on.
This can be useful in testing your application from within JUnit tests. It internally uses Jetty as the servlet container.

Example usage:
```java
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
```
Refer to tests: [RequestLineStubbingSpec](/src/test/scala/com/thoughtworks/webstub/RequestLineStubbingSpec.scala), [HeaderStubbingSpec](/src/test/scala/com/thoughtworks/webstub/HeaderStubbingSpec.scala), [BodyContentStubbingSpec](/src/test/scala/com/thoughtworks/webstub/BodyContentStubbingSpec.scala), [MultipleContextStubbingSpec](/src/test/scala/com/thoughtworks/webstub/MultipleContextStubbingSpec.scala).

For a real example, refer to [WebStubDemo](https://github.com/tusharm/WebStubDemo) where a real Spring application is tested using WebStub and [Inproctester](https://github.com/aharin/inproctester)
The example uses the latest released version of WebStub and hence there might be minor differences to the example shown above.

## Features

- Stubs a real server (embedded Jetty) on-the-fly from within your tests (so test data setup lies in the test) using a fluent DSL
- Supports GET, POST, PUT and DELETE verbs
- Supports stubbing requests (method, uri, query params, headers, content) and responses (status code, headers, content)
- Allows resetting stub configuration before/after every test, to keep individual tests independent
- Requests/responses can take [ContentBuilder](/src/main/java/com/thoughtworks/webstub/dsl/builders/ContentBuilder.java) implementations; create ContentBuilders (e.g. to convert your domain objects to JSON or XML) to suit your needs
- Supports stubbing multiple web contexts on a single server
- Can be used for functional testing of services running externally or inside the test itself

## Releases

The latest released version is 1.0.1

The artifact is available at [Maven Central Repository](http://search.maven.org/#search%7Cga%7C1%7Cweb-stub).
The maven co-ordinates are:
```
<dependency>
  <groupId>com.thoughtworks</groupId>
  <artifactId>web-stub</artifactId>
  <version>x.y.z</version>
</dependency>
```

The release notes are available [here](https://github.com/tusharm/WebStub/wiki/ReleaseNotes).

## RoadMap

- Support for https
- Support for auth
- Other HTTP verbs
- Starting stub servers in-process, rather than on real native ports
- Dashboard for the stub server
- Better reporting of assertion failures

## License

WebStub is distributed under the terms of Apache Software License v2.0: http://www.apache.org/licenses/LICENSE-2.0.html
