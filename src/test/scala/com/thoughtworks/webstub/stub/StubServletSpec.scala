package com.thoughtworks.webstub.stub

import org.mockito.Mockito._
import org.mockito.Matchers._
import org.mockito.Matchers.{ eq  => mockitoEq }
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.{Response, Request, HttpConfiguration}
import java.io.{BufferedReader, StringReader, PrintWriter, StringWriter}

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

  it ("should return client error if POST doesn't contain content, while its stub configuration does") {
    val servlet = new StubServlet(httpConfiguration("POST", "/employees", "Bruce Willis", 201))

    val request = mockRequest("POST", "/employees")
    val response = mock[HttpServletResponse]
    servlet.doPost(request, response)

    verify(request).getReader
    verify(response).sendError(mockitoEq(404), anyString());
  }

  it ("should return response if POST contains content matching the stub configuration") {
    val servlet = new StubServlet(httpConfiguration("POST", "/employees", "Bruce Willis", 201))

    val request = mockRequest("POST", "/employees", "Bruce Willis")
    val response = mock[HttpServletResponse]
    servlet.doPost(request, response)

    verify(request).getReader
    verify(response).setStatus(201);
  }

  private def mockRequest(method: String, uri: String, content: String = "") = {
    val request = mock[HttpServletRequest]
    when(request.getMethod).thenReturn(method)
    when(request.getServletPath).thenReturn(uri);
    when(request.getReader).thenReturn(new BufferedReader(new StringReader(content)));
    request
  }

  private def mockResponseWithWriter(writer: StringWriter) = {
    val response = mock[HttpServletResponse]
    when(response.getWriter).thenReturn(new PrintWriter(writer))
    response
  }

  private def httpConfiguration(method: String, uri: String, status: Int, responseContent: String) =
    new HttpConfiguration(new Request(method, uri), new Response(status, responseContent))

  private def httpConfiguration(method: String, uri: String, requestContent: String, status: Int) =
    new HttpConfiguration(new Request(method, uri, requestContent), new Response(status, null))
}
