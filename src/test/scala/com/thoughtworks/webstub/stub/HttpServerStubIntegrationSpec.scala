package com.thoughtworks.webstub.stub

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.StubServerFactory
import StubServerFactory.stubServer
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.utils.Client
import com.thoughtworks.webstub.dsl.ResponseBuilder.response
import com.thoughtworks.webstub.dsl.HttpDsl.dslWrapped
import scala.collection.JavaConversions._

@RunWith(classOf[JUnitRunner])
class HttpServerStubIntegrationSpec extends SmartSpec {
  val httpClient = new Client
  val contextUrl = "http://localhost:9099/context"

  val stub = stubServer(9099, "/context")
  val dslServer = dslWrapped(stub)

  override protected def beforeAll() {
    stub.start
  }

  override protected def beforeEach() {
    stub.reset
  }

  override protected def afterAll() {
    stub.stop
  }

  it("should support GET") {
    dslServer.get("/person").returns(response(200))
    httpClient.get(s"$contextUrl/person").status should be(200)
    httpClient.get(s"$contextUrl/person/1").status should be(404)
  }

  it("should support DELETE") {
    dslServer.delete("/person").returns(response(200))
    httpClient.delete(s"$contextUrl/person").status should be(200)
  }

  it("should support POST") {
    dslServer.post("/person").returns(response(200))
    httpClient.post(s"$contextUrl/person").status should be(200)
  }

  it("should support PUT") {
    // note that no expectation is set on stub for request content, yet it matches response
    dslServer.put("/person").returns(response(201))
    httpClient.put(s"$contextUrl/person", "some content").status should be(201)
  }

  it("should support response body content") {
    dslServer.get("/person").returns(response(200).withContent("Some person"))

    httpClient.get(s"$contextUrl/person") should have(
      'status(200),
      'content("Some person")
    )
  }

  it("should support adding response headers") {
    List(
      response(200).withHeader("Content-Length", "13"),
      response(200).withHeaders(Map("Content-Length" -> "13"))
    ).foreach {
      response =>
        dslServer.get("/users/1").returns(response)

        val resp = httpClient.get(s"$contextUrl/users/1")
        resp.header("Content-Length") should contain("13")
    }
  }

  describe("for entity enclosing requests") {
    it("should support POST with request body") {
      dslServer.post("/users").withContent("new user").returns(response(201))

      httpClient.post(s"$contextUrl/users").status should be(400)
      httpClient.post(s"$contextUrl/users", "new user").status should be(201)
    }

    it("should support PUT with request body") {
      dslServer.put("/user/1").withContent("modified user").returns(response(201))
      httpClient.put(s"$contextUrl/user/1", "modified user").status should be(201)
    }
  }

  it("should support multiple expectations on the server") {
    dslServer.get("/person/1").returns(response(404))
    dslServer.delete("/person/2").returns(response(200))

    httpClient.get(s"$contextUrl/person/1").status should be(404)
    httpClient.delete(s"$contextUrl/person/2").status should be(200)
  }
}
