package com.thoughtworks.webstub.stub

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.ServerStubFactory
import ServerStubFactory.dslServer
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.utils.Client
import com.thoughtworks.webstub.dsl.ResponseBuilder.response

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

    server.get("/person/1").returns(response().withStatus(302))
    httpClient.get("http://localhost:9091/context/person/1").status should be(302)

    server.stop
  }

  it("should allow resetting all stubbed calls") {
    val base = "http://localhost:9091/context"
    val server = dslServer(9091, "/context")
    server.start

    server.get("/person/1").returns(response().withStatus(302))
    server.reset
    httpClient.get(base + "/person/1").status should be(404)

    server.stop
  }
}
