package com.thoughtworks.test.web.stub

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.test.SmartSpec
import com.thoughtworks.test.web.utils.Client
import com.thoughtworks.test.web.dsl.Response
import com.thoughtworks.test.web.server.JettyHttpServer

@RunWith(classOf[JUnitRunner])
class HttpServerStubIntegrationSpec extends SmartSpec {
  val httpClient = new Client

  it("should start and stop the server") {
    val server = new HttpServerStub(new JettyHttpServer(9091, "/context"))
    val statusPageUrl = "http://localhost:9091/context/status"

    server.start
    httpClient.get(statusPageUrl).status should be(200)

    server.stop
    evaluating {
      httpClient.get(statusPageUrl).status
    } should produce[Exception]
  }

  it("should stub HTTP calls") {
    val server = new HttpServerStub(new JettyHttpServer(9091, "/context"))
    server.start

    server.get("/person/1").returns(new Response(302))
    httpClient.get("http://localhost:9091/context/person/1").status should be(302)

    server.stop
  }

  ignore("should allow resetting all stubbed calls") {
    val base = "http://localhost:9091/context"
    val server = new HttpServerStub(new JettyHttpServer(9091, "/context"))
    server.start

    server.get("/person/1").returns(new Response(302))
    server.reset
    httpClient.get(base + "/person/1").status should be(404)

    server.stop
  }
}
