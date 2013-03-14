WebStub

This library intends to simplify stubbing out responses from external HTTP entities that your application/service  depends on.
This can be useful in testing your application from within JUnit tests. 

Tests will setup the stubs like this:

	externalService.get("/accounts/1").returns(response().withStatus(200))

In particular, I think it will be useful in tests which use https://github.com/aharin/inproctester.

Currently, this is in a very basic state. Lots more to come..

