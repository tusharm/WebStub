package com.thoughtworks.webstub

import dsl.builders.ResponseBuilder._
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import StubServer._

@RunWith(classOf[JUnitRunner])
class BodyContentStubbingSpec extends StubFunctionalSpec {
  val server = newServer(9099)
  val contextService = server.stub("/context")

  val content = <user>
    <firstName>John</firstName>
    <lastName>Doe</lastName>
    <dob>03/03/1978</dob>
  </user>.toString

  val contextUrl = "http://localhost:9099/context"

  override protected def beforeEach() {
    contextService.reset
  }

  describe("for entity enclosing requests") {
    it("should support POST with request body") {
      contextService.post("/users").withContent(content).returns(response(201))

      httpClient.post(s"$contextUrl/users").status should be(400)
      httpClient.post(s"$contextUrl/users", content).status should be(201)
    }

    it("should support PUT with request body") {
      contextService.put("/user/1").withContent("modified user").returns(response(201))
      httpClient.put(s"$contextUrl/user/1", "modified user").status should be(201)
    }
  }

  it("should support response body content") {
    contextService.get("/person").returns(response(200).withContent(content))

    httpClient.get(s"$contextUrl/person") should have(
      'status(200),
      'content(content)
    )
  }
}
