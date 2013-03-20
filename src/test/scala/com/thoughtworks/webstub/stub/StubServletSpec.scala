package com.thoughtworks.webstub.stub

import org.mockito.Mockito._
import org.mockito.Matchers._
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.{Response, Request, HttpConfiguration}
import java.io.{PrintWriter, StringWriter}

@RunWith(classOf[JUnitRunner])
class StubServletSpec extends SmartSpec {
  val servlet = new StubServlet(httpConfiguration("GET", "/test", 302, "Bruce Willis"))

  it("should return the configured response, if method and uri match") {
    val writer = new StringWriter
    val response = mockResponseWithWriter(writer)

    servlet.doGet(mockRequest("GET", "/test"), response)

    verify(response).setStatus(302)
    writer.toString should be("Bruce Willis")
  }

  it("should return server error if method is not supported") {
    val response = mock[HttpServletResponse]
    servlet.doPost(mockRequest("POST", "/test"), response)

    verify(response).sendError(501)
    verify(response, never()).setStatus(any())
  }

  it("should return client error if uri doesn't match") {
    val response = mock[HttpServletResponse]
    servlet.doGet(mockRequest("GET", "/non-existent"), response)

    verify(response).sendError(404)
    verify(response, never()).setStatus(any())
  }

  private def mockRequest(method: String, uri: String) = {
    val request = mock[HttpServletRequest]
    when(request.getMethod).thenReturn(method)
    when(request.getServletPath).thenReturn(uri);
    request
  }

  private def mockResponseWithWriter(writer: StringWriter) = {
    val response = mock[HttpServletResponse]
    when(response.getWriter).thenReturn(new PrintWriter(writer))
    response
  }

  private def httpConfiguration(method: String, uri: String, status: Int, content: String) = new HttpConfiguration(new Request(method, uri), new Response(status, content))
}
