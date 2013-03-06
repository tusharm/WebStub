package com.thoughtworks.test.web

import dsl.ExpectedResponseBuilder.response
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.test.SmartSpec
import utils.Client

@RunWith(classOf[JUnitRunner])
class HttpServerStubSpec extends SmartSpec with WellBehavedServer {
  val server = new HttpServerStub(9099, "context")
  val httpClient = new Client

  describe ("HttpServer stub") {

    describe ("for GET requests") {

      ignore ("should stub response status") {
        val url = "http://localhost:9099/context/blah"

        httpClient.get(url).status should be(404)
        server.get("blah").returns(response().withStatus(200))
        httpClient.get(url).status should be(200)
      }

    }

  }
}
