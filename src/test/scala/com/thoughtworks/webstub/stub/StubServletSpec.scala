package com.thoughtworks.webstub.stub

import org.mockito.Mockito._
import org.mockito.Matchers._
import org.mockito.Matchers.{eq => mockitoEq}
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.{Response, Request, HttpConfiguration}
import java.io.{BufferedReader, StringReader, PrintWriter, StringWriter}

@RunWith(classOf[JUnitRunner])
class StubServletSpec extends SmartSpec {

  it("should return the configured response content") {
    val servlet = new StubServlet(httpConfiguration("GET", "/test", 302, "Bruce Willis"))
    val writer = new StringWriter
    val response = mockResponseWithWriter(writer)

    servlet.doGet(mockRequest("GET", "/test"), response)

    verify(response).setStatus(302)
    writer.toString should be("Bruce Willis")
  }


  it("should return the configured response for POST , PUT, DELETE") {
    List("POST", "PUT", "DELETE").foreach {
      method =>
        val servlet = new StubServlet(httpConfiguration(method, "/test", 200))
        val response = mock[HttpServletResponse]

        servlet.doGet(mockRequest(method, "/test"), response)

        verify(response).setStatus(200)
    }
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

  private def httpConfiguration(method: String, uri: String, status: Int, responseContent: String = null) =
    new HttpConfiguration(new Request(method, uri), new Response(status, responseContent))

}
