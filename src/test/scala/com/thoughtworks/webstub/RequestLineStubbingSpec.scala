package com.thoughtworks.webstub

import com.thoughtworks.webstub.StubServer._
import com.thoughtworks.webstub.dsl.builders.ResponseBuilder._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class RequestLineStubbingSpec extends StubFunctionalSpec {
  val contextUrl = "http://localhost:9099/people"

  val server = newServer(9099)
  val peopleService = server.stub("/people")

  override protected def beforeEach() {
    peopleService.reset
  }

  it ("should support query params") {
    peopleService.get("/person?age=30&status=single").returns(response(200))

    httpClient.getWithParams(s"$contextUrl/person?age=30&status=single").status should be(200)
    httpClient.get(s"$contextUrl/person").status should be(400)
  }

  it("should support multiple expectations") {
    peopleService.get("/person/1").returns(response(404))
    peopleService.delete("/person/2").returns(response(200))

    httpClient.get(s"$contextUrl/person/1").status should be(404)
    httpClient.delete(s"$contextUrl/person/2").status should be(200)
  }

  it("should support multiple expectations on the same uri") {
    peopleService.get("/person/1").returns(response(200))
    peopleService.delete("/person/1").returns(response(405))

    httpClient.get(s"$contextUrl/person/1").status should be(200)
    httpClient.delete(s"$contextUrl/person/1").status should be(405)
  }

  it("should support overriding expectations") {
    peopleService.get("/person/1").returns(response(200))
    peopleService.get("/person/1").returns(response(405))

    httpClient.get(s"$contextUrl/person/1").status should be(405)
  }

  it("should support wildcard urls") {
    peopleService.get("/person/*").returns(response(200))
    httpClient.get(s"$contextUrl/person/1").status should be(200)
  }
}
