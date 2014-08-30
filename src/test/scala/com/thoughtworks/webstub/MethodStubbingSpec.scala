package com.thoughtworks.webstub

import dsl.builders.ResponseBuilder._
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import StubServer._

@RunWith(classOf[JUnitRunner])
class MethodStubbingSpec extends StubFunctionalSpec {
  val contextUrl = "http://localhost:9099/context"

  val server = newServer(9099)
  val contextService = server.stub("/context")

  override protected def beforeEach() {
    contextService.reset
  }

  it("should support GET") {
    contextService.get("/person").returns(response(200))

    httpClient.get(s"$contextUrl/person").status should be(200)
    httpClient.get(s"$contextUrl/person/1").status should be(404)
  }

  it("should support DELETE") {
    contextService.delete("/person").returns(response(200))

    httpClient.delete(s"$contextUrl/person").status should be(200)
  }

  it("should support POST") {
    contextService.post("/person").returns(response(200))
    httpClient.post(s"$contextUrl/person").status should be(200)
  }

  it("should support PUT") {
    // note that no expectation is set on stub for request content, yet it matches response
    contextService.put("/person").returns(response(201))

    httpClient.put(s"$contextUrl/person", "some content").status should be(201)
  }

  it("should support OPTIONS") {
    contextService.options("/person").returns(response(401))

    httpClient.options(s"$contextUrl/person").status should be(401)
  }

  it("should support HEAD") {
    contextService.head("/person").returns(response(401))

    httpClient.head(s"$contextUrl/person").status should be(401)
  }

  it("should support TRACE") {
    contextService.trace("/person").returns(response(200).withContent("All Ok"))

    httpClient.trace(s"$contextUrl/person").status should be(200)
  }

  it("should support PATCH") {
    contextService.patch("/person/1").withContent("files diffs").returns(response(204))

    httpClient.patch(s"$contextUrl/person/1", "files diffs").status should be(204)
  }
}
