package com.thoughtworks.webstub.stub.matcher

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import javax.servlet.http.HttpServletRequest
import com.thoughtworks.webstub.config.{Request, HttpConfiguration}
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class UriMatcherSpec extends SmartSpec {
  val matcher = new UriMatcher(requestFor("/test"))

  it ("should succeed if the request uri matches that in configuration, ignoring query params") {
    matcher.matches(httpConfiguration("/test")) should be(true)
    matcher.matches(httpConfiguration("/test?a=b")) should be(true)
  }

  it ("should return false if uri doesn't match") {
    matcher.matches(httpConfiguration("/test2")) should be(false)
    matcher.failedResponseCode should be(404)
  }

  it ("should match glob patterns") {
    var matcher = new UriMatcher(requestFor("/test/1/2"))
    matcher.matches(httpConfiguration("/test/*")) should be(true)

    matcher = new UriMatcher(requestFor("/test/1?a=b"))
    matcher.matches(httpConfiguration("/test/*")) should be(true)
  }


  private def httpConfiguration(requestUri: String) = new HttpConfiguration(new Request("", requestUri), null)

  private def requestFor(uri: String) = {
    val request = mock[HttpServletRequest]
    when(request.getServletPath).thenReturn(uri)
    request
  }
}
