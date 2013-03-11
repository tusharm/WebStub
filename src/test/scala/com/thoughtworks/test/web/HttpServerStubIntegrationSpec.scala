package com.thoughtworks.test.web

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
    ignore ("should reset all stubs"){}

    describe ("for GET requests") {
      ignore ("should stub response status"){}
    }
  }
}
