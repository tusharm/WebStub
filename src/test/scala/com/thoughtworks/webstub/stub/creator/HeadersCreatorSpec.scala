package com.thoughtworks.webstub.stub.creator

import com.thoughtworks.webstub.SmartSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.config.{Response, HttpConfiguration}
import scala.collection.JavaConversions._
import com.thoughtworks.webstub.dsl.Header
import javax.servlet.http.HttpServletResponse
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class HeadersCreatorSpec extends SmartSpec {

  it ("should set headers on the response") {
    val configuration = configurationWithResponseHeaders(new Header("name", "value"))
    val response = mock[HttpServletResponse]

    new HeadersCreator(configuration).applyOn(response)

    verify(response).setHeader("name", "value")
  }

  def configurationWithResponseHeaders(headers: Header*): HttpConfiguration = {
    new HttpConfiguration(null, new Response(0, null, headers))
  }
}
