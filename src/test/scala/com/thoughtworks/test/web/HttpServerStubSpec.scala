package com.thoughtworks.test.web

import com.thoughtworks.test.SmartSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import utils.Client

@RunWith(classOf[JUnitRunner])
class HttpServerStubSpec extends SmartSpec {
  val httpClient = new Client
  val serverStub = new HttpServerStub(9099, "context")

  describe ("HttpServer stub") {
    describe ("for GET requests") {
      ignore("should stub response status") {
        httpClient.get("http://localhost:9099/root/blah").status should be(404)
        serverStub.get("blah").returns()
        httpClient.get("http://localhost:9099/root/blah").status should be(200)
      }
    }
  }
}
