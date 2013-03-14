package com.thoughtworks.test.web.stub

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.test.SmartSpec
import com.thoughtworks.test.web.utils.Client
import com.thoughtworks.test.web.dsl.Response
import com.thoughtworks.test.web.ServerStubFactory.dslServer

@RunWith(classOf[JUnitRunner])
class HttpServerStubIntegrationSpec extends SmartSpec {
  val httpClient = new Client

  it("should start and stop the server") {
    val statusPageUrl = "http://localhost:9091/context/status"
    val server = dslServer(9091, "/context")

    server.start
    httpClient.get(statusPageUrl).status should be(200)

    server.stop
    evaluating {
      httpClient.get(statusPageUrl).status
    } should produce[Exception]
  }

  it("should stub HTTP calls") {
    val server = dslServer(9091, "/context")
    server.start

    server.get("/person/1").returns(new Response(302))
    httpClient.get("http://localhost:9091/context/person/1").status should be(302)

    server.stop
  }

  it("should allow resetting all stubbed calls") {
    val base = "http://localhost:9091/context"
    val server = dslServer(9091, "/context")
    server.start

    server.get("/person/1").returns(new Response(302))
    server.reset
    httpClient.get(base + "/person/1").status should be(404)

    server.stop
  }
}
