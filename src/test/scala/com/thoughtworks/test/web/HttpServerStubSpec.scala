package com.thoughtworks.test.web

import com.thoughtworks.test.SmartSpec
import utils.Client
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class HttpServerStubSpec extends SmartSpec {
  val httpClient = new Client
  val serverStub = new HttpServerStub(9099, "root")

  override protected def beforeAll {
    serverStub.start
  }

  describe ("An HttpServer stub") {
    it ("should display a status page if started") {
      httpClient.get("http://localhost:9099/root/status").status should be(200)
    }

    it ("must not accept an invalid context root") {
      evaluating (new HttpServerStub(9099, null)) should produce[IllegalArgumentException]
    }

    it ("should report resource not found if context doesn't exist") {
      httpClient.get("http://localhost:9099/doesNotExist/status").status should be(404)
    }
  }

  override protected def afterAll {
    serverStub.stop
  }
}
