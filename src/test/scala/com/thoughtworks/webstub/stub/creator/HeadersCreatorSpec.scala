package com.thoughtworks.webstub.stub.creator

import com.thoughtworks.webstub.SmartSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.config.{Response, HttpConfiguration}
import scala.collection.JavaConversions._
import com.thoughtworks.webstub.dsl.Header
import javax.servlet.http.HttpServletResponse
import org.mockito.Mockito._
import org.mockito.Matchers._

@RunWith(classOf[JUnitRunner])
class HeadersCreatorSpec extends SmartSpec {

  it ("should set headers on the response") {
    val configuration = configurationWithResponseHeaders(new Header("name", "value"))
    val response = mock[HttpServletResponse]

    new HeadersCreator(configuration).createFor(response)
    verify(response).setHeader("name", "value")
  }

  it ("should not modify response if no headers configured") {
    val configuration = configurationWithResponseHeaders()
    val response = mock[HttpServletResponse]

    new HeadersCreator(configuration).createFor(response)
    verify(response, never()).setHeader(anyString(), anyString())
  }

  def configurationWithResponseHeaders(headers: Header*) = new HttpConfiguration(null, new Response(0, null, headers))
}
