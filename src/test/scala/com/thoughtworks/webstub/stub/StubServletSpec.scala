package com.thoughtworks.webstub.stub

import org.mockito.Mockito._
import org.mockito.Matchers._
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.StubConfiguration

@RunWith(classOf[JUnitRunner])
class StubServletSpec extends SmartSpec {
  val servlet = new StubServlet(new StubConfiguration("GET", "/test", 302))

  it("should return the configured response, if method and uri match") {
    val response = mock[HttpServletResponse]
    servlet.doGet(mockRequest("GET", "/test"), response)

    verify(response).setStatus(302)
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
}
