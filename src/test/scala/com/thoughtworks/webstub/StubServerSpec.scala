package com.thoughtworks.webstub

import com.thoughtworks.webstub.StubServer._

class StubServerSpec extends StubFunctionalSpec {
  val server = newServer(9099).withWebConsole

  it ("should not stub root") {
    evaluating {
      server.stub("/")
    } should produce[IllegalArgumentException]
  }

  it ("should host a web console on root") {
    val response = httpClient.get("http://localhost:9099/")
    response.status() should be(200)
  }
}
