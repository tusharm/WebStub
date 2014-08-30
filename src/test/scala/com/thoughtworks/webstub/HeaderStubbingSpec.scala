package com.thoughtworks.webstub

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import StubServer._
import com.thoughtworks.webstub.dsl.builders.ResponseBuilder
import ResponseBuilder.response
import scala.collection.JavaConversions._
import org.apache.http.message.BasicHeader

@RunWith(classOf[JUnitRunner])
class HeaderStubbingSpec extends StubFunctionalSpec {
  val contextUrl = "http://localhost:9099/context"

  val server = newServer(9099)
  val contextService = server.stub("/context")

  override protected def beforeEach() {
    contextService.reset
  }

  it("should support request headers") {
    def requestWithHeaders = httpClient.get(s"$contextUrl/users/1", List(new BasicHeader("name", "value")))

    contextService.get("/users/1").withHeader("name", "value").returns(response(200))

    httpClient.get(s"$contextUrl/users/1").status should be(412)
    requestWithHeaders.status should be(200)

    contextService.get("/users/1").withHeaders(Map("name" -> "value")).returns(response(200))
    requestWithHeaders.status should be(200)
  }

  it("should support response headers") {
    List(
      response(200).withHeader("Content-Length", "13"),
      response(200).withHeaders(Map("Content-Length" -> "13"))
    ).foreach {
      response =>
        contextService.get("/users/1").returns(response)

        val resp = httpClient.get(s"$contextUrl/users/1")
        resp.header("Content-Length") should contain("13")
    }
  }
}
