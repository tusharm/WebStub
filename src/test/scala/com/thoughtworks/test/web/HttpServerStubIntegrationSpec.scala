package com.thoughtworks.test.web

import dsl.ExpectedResponseBuilder.response
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.test.SmartSpec
import server.WellBehavedServer
import utils.Client

@RunWith(classOf[JUnitRunner])
class HttpServerStubIntegrationSpec extends SmartSpec with WellBehavedServer {
  val server = new HttpServerStub(9099, "context")
  val httpClient = new Client

  describe ("HttpServer stub") {

    ignore ("should reset all stubs") {
      server.get("blah/*").returns(response().withStatus(200))
      server.reset
      httpClient.get("http://localhost:9099/context/blah").status should be(404)
    }

    describe ("for GET requests") {

      ignore ("should stub response status") {
        val url = "http://localhost:9099/context/blah"

        httpClient.get(url).status should be(404)

        server.get("blah/*").returns(response().withStatus(200))

        httpClient.get(url).status should be(200)
      }

    }

  }
}
