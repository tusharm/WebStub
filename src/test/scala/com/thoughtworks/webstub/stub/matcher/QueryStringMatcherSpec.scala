package com.thoughtworks.webstub.stub.matcher

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.{Request, HttpConfiguration}
import javax.servlet.http.HttpServletRequest
import org.mockito.Mockito._
import java.net.URLEncoder

@RunWith(classOf[JUnitRunner])
class QueryStringMatcherSpec extends SmartSpec {
  it ("should succeed if configuration contains no query string") {
    val matcher = new QueryStringMatcher(requestWith("a=b"))
    matcher.matches(httpConfiguration("/test")) should be(true)
  }

  it ("should succeed if query string matches that in configuration") {
    val matcher = new QueryStringMatcher(requestWith("name=John Doe"))
    matcher.matches(httpConfiguration("/user?name=John Doe")) should be(true)
  }

  it ("should fail if query strings do not match") {
    val matcher = new QueryStringMatcher(requestWith("name=John"))
    matcher.matches(httpConfiguration("/user?name=John Doe")) should be(false)
    matcher.failedResponseCode should be(400)
  }

  private def httpConfiguration(requestUri: String) = new HttpConfiguration(new Request("", requestUri), null)

  private def requestWith(queryString: String) = {
    val request = mock[HttpServletRequest]
    when(request.getQueryString).thenReturn(URLEncoder.encode(queryString, "UTF-8"))
    request
  }
}
