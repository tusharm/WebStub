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
Refer to tests:
+ [MethodStubbingSpec](/src/test/scala/com/thoughtworks/webstub/MethodStubbingSpec.scala).
+ [HeaderStubbingSpec](/src/test/scala/com/thoughtworks/webstub/HeaderStubbingSpec.scala).
+ [BodyContentStubbingSpec](/src/test/scala/com/thoughtworks/webstub/BodyContentStubbingSpec.scala).

In particular, I think it will be useful in tests which use [inproctester](https://github.com/aharin/inproctester).

Access the repo at https://oss.sonatype.org/content/repositories/snapshots/, give the latest snapshot a ride and provide your feedback.
The maven co-ordinates are:
```
<dependency>
  <groupId>com.thoughtworks</groupId>
  <artifactId>web-stub</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```
## Features

- Stubs a real server (embedded Jetty) on-the-fly from within your tests (so test data setup lies in the test) using a fluent DSL
- Supports GET, POST, PUT and DELETE verbs
- Supports stubbing requests (method, uri, headers, content) and responses (status code, headers, content)
- Allows resetting stub configuration before/after every test, to keep individual tests independent
- Requests/responses can take [ContentBuilder](/src/main/java/com/thoughtworks/webstub/dsl/builders/ContentBuilder.java) implementations; create ContentBuilders (e.g. to convert your domain objects to JSON or XML) to suit your needs
- Can be used for functional testing of services running externally or inside the test itself (e.g. using [inproctester](https://github.com/aharin/inproctester))

## RoadMap

- Support for https
- Support for auth
- Other HTTP verbs
- Starting stub servers in-process, rather than on real native ports
- Dashboard for the stub server
- Better reporting of assertion failures

## License

WebStub is distributed under the terms of Apache Software License v2.0: http://www.apache.org/licenses/LICENSE-2.0.html
