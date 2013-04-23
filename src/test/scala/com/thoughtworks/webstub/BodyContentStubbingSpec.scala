package com.thoughtworks.webstub

import dsl.builders.ResponseBuilder._
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import StubServerFacade._

@RunWith(classOf[JUnitRunner])
class BodyContentStubbingSpec extends StubFunctionalSpec {
  val contextUrl = "http://localhost:9099/context"

  val server = newServer(9099)
  val dslServer = server.withContext("/context")

  override protected def beforeEach() {
    dslServer.reset
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

  it("should support response body content") {
    dslServer.get("/person").returns(response(200).withContent("Some person"))

    httpClient.get(s"$contextUrl/person") should have(
      'status(200),
      'content("Some person")
    )
  }
}
