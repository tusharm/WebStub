package com.thoughtworks.webstub

import dsl.builders.ResponseBuilder._
import dsl.HttpDsl._
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import StubServerFacade._

@RunWith(classOf[JUnitRunner])
class RequestLineStubbingSpec extends StubFunctionalSpec {
  val contextUrl = "http://localhost:9099/context"

  val server = newServer(9099)
  val dslServer = server.withContext("/context")

  override protected def beforeEach() {
    dslServer.reset
  }

  it ("should support query params") {
    dslServer.get("/dogs?color=black&status=new born").returns(response(200))

    httpClient.getWithParams(s"$contextUrl/dogs?color=black&status=new born").status should be(200)
    httpClient.get(s"$contextUrl/dogs").status should be(400)
  }

  it("should support multiple expectations") {
    dslServer.get("/person/1").returns(response(404))
    dslServer.delete("/person/2").returns(response(200))

    httpClient.get(s"$contextUrl/person/1").status should be(404)
    httpClient.delete(s"$contextUrl/person/2").status should be(200)
  }

  it("should support multiple expectations on the same uri") {
    dslServer.get("/person/1").returns(response(200))
    dslServer.delete("/person/1").returns(response(405))

    httpClient.get(s"$contextUrl/person/1").status should be(200)
    httpClient.delete(s"$contextUrl/person/1").status should be(405)
  }

  it("should support overriding expectations") {
    dslServer.get("/person/1").returns(response(200))
    dslServer.get("/person/1").returns(response(405))

    httpClient.get(s"$contextUrl/person/1").status should be(405)
  }

  it("should support wildcard urls") {
    dslServer.get("/person/*").returns(response(200))
    httpClient.get(s"$contextUrl/person/1").status should be(200)
  }
}
