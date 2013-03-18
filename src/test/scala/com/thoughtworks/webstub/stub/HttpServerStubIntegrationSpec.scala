package com.thoughtworks.webstub.stub

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.StubServerFactory
import StubServerFactory.stubServer
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.utils.{Response, Client}
import com.thoughtworks.webstub.dsl.ResponseBuilder.response
import com.thoughtworks.webstub.dsl.HttpDsl.dslWrapped
import com.thoughtworks.webstub.dsl.HttpDsl

@RunWith(classOf[JUnitRunner])
class HttpServerStubIntegrationSpec extends SmartSpec {
  val httpClient = new Client
  val contextUrl = "http://localhost:9099/context"

  val stub = stubServer(9099, "/context")
  val dslServer = dslWrapped(stub)

  override protected def beforeAll() { stub.start }
  override protected def beforeEach() { stub.reset }
  override protected def afterAll() { stub.stop }

  it("should support GET") {
    expectAndAssert(dslServer.get, httpClient.get)
  }

  it("should support POST") {
    expectAndAssert(dslServer.post, httpClient.post)
  }

  it("should support PUT") {
    expectAndAssert(dslServer.put, httpClient.put)
  }

  it("should support DELETE") {
    expectAndAssert(dslServer.delete, httpClient.delete)
  }

  private def expectAndAssert(expectedRequest: String => HttpDsl, actualRequest: String => Response) {
    expectedRequest("/person").returns(response(200))
    actualRequest(s"$contextUrl/person").status should be(200)
  }
}
