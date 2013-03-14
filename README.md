# WebStub

This library intends to simplify stubbing out responses from external HTTP entities that your application/service  depends on.
This can be useful in testing your application from within JUnit tests. 

Tests will setup a stub like this:
```java
externalService.get("/accounts/1").returns(response().withStatus(200))
```
In particular, I think it will be useful in tests which use https://github.com/aharin/inproctester.

Currently, this is in a very basic state. Lots more to come..

## RoadMap

### Immediate:
+ POST, PUT, DELETE
+ request/response body content
+ request/response headers
+ https
+ starting stub servers in-process, rather than on real native ports

## License

WebStub is distributed under the terms of Apache Software License v2.0: http://www.apache.org/licenses/LICENSE-2.0.html
