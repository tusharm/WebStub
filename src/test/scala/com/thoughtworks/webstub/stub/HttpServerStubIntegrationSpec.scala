package com.thoughtworks.webstub.stub

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.ServerStubFactory
import ServerStubFactory.dslServer
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.utils.{Response, Client}
import com.thoughtworks.webstub.dsl.ResponseBuilder.response
import com.thoughtworks.webstub.dsl.DslProvider

@RunWith(classOf[JUnitRunner])
class HttpServerStubIntegrationSpec extends SmartSpec {
  val stubServer = dslServer(9099, "/context")
  val httpClient = new Client
  val contextUrl = "http://localhost:9099/context"

  override protected def beforeAll() { stubServer.start }
  override protected def beforeEach() { stubServer.reset }
  override protected def afterAll() { stubServer.stop }

  it("should support GET") {
    expectAndAssert(stubServer.get, httpClient.get)
  }

  it("should support POST") {
    expectAndAssert(stubServer.post, httpClient.post)
  }

  it("should support PUT") {
    expectAndAssert(stubServer.put, httpClient.put)
  }

  it("should support DELETE") {
    expectAndAssert(stubServer.delete, httpClient.delete)
  }

  private def expectAndAssert(expectOperationOn: String => DslProvider, assertOperationOn: String => Response) {
    expectOperationOn("/person").returns(response().withStatus(200))
    assertOperationOn(s"$contextUrl/person").status should be(200)
  }
}
