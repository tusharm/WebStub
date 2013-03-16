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
  val stubServer = dslServer(9099, "/context")
  val httpClient = new Client
  val contextUrl = "http://localhost:9099/context"

  override protected def beforeAll() { stubServer.start }
  override protected def beforeEach() { stubServer.reset }
  override protected def afterAll() { stubServer.stop }

  it("should support GET") {
    stubServer.get("/person/1").returns(response().withStatus(302))
    httpClient.get(s"$contextUrl/person/1").status should be(302)
  }

  it("should support POST") {
    stubServer.post("/person").returns(response().withStatus(202))
    httpClient.post(s"$contextUrl/person").status should be(202)
  }
}
