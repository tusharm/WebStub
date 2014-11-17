package com.thoughtworks.webstub

import com.thoughtworks.webstub.StubServer._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
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

  it("should start a server on random port") {
    val server = newServer.withWebConsole
    server.start

    try {
      val response = httpClient.get(s"http://localhost:${server.port}")
      response.status should be(200)
    } finally {
      server.stop
    }
  }
}
