# WebStub [![Build Status](https://snap-ci.com/tusharm/go-artifactory-plugin/branch/master/build_image)](https://snap-ci.com/tusharm/go-artifactory-plugin/branch/master)

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
Refer to [functional specs](/src/test/scala/com/thoughtworks/webstub/) for more detals.

For a real example, refer to [WebStubDemo](https://github.com/tusharm/WebStubDemo) where a real Spring application is tested using WebStub and [Inproctester](https://github.com/aharin/inproctester)
The example uses the latest released version of WebStub and hence there might be minor differences to the example shown above.

## Features

- Stubs a real server (embedded Jetty) on-the-fly from within your tests (so test data setup lies in the test) using a fluent DSL
- Supports GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE and PATCH
- Supports stubbing requests (method, uri, query params, headers, content) and responses (status code, headers, content)
- Allows resetting stub configuration before/after every test, to keep individual tests independent
- Requests/responses can take [ContentBuilder](/src/main/java/com/thoughtworks/webstub/dsl/builders/ContentBuilder.java) implementations; create ContentBuilders (e.g. to convert your domain objects to JSON or XML) to suit your needs
- Supports stubbing multiple web contexts on a single server
- Can be used for functional testing of services running externally or inside the test itself

## Releases

The latest released version is 1.1.0

The released artifact is available at [Maven Central Repository](http://search.maven.org/#search%7Cga%7C1%7Cweb-stub), while the latest snapshot is available [here](https://oss.sonatype.org/content/repositories/snapshots/).
The maven co-ordinates are:
```
<dependency>
  <groupId>com.thoughtworks</groupId>
  <artifactId>web-stub</artifactId>
  <version>x.y.z</version>
</dependency>
```

The release notes are available on the [Wiki](https://github.com/tusharm/WebStub/wiki).

## RoadMap

- Parallel test execution
- Delayed and chaotic responses, to simulate unreliable external dependencies
- Support for https
- Support for auth
- Starting stub servers in-process, rather than on real native ports
- Dashboard for the stub server

## Get involved

- Report bugs
- Suggest features
- Fork, collaborate

## License

WebStub is distributed under the terms of Apache Software License v2.0: http://www.apache.org/licenses/LICENSE-2.0.html
