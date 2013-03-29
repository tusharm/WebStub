package com.thoughtworks.webstub.stub.creator

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.{Response, HttpConfiguration}
import scala.collection.JavaConversions._
import javax.servlet.http.HttpServletResponse
import org.mockito.Mockito._
import java.io.{PrintWriter, StringWriter}

@RunWith(classOf[JUnitRunner])
class ContentCreatorSpec extends SmartSpec {


  it ("should add content to the response") {
    val configuration =  configurationWithResponseContent("content")
    val writer = new StringWriter
    val response = responseWithWriter(writer)

    new ContentCreator(configuration).createFor(response)

    writer.toString should be("content")
  }

  it ("should not set response if there is no content configured") {
    val configuration =  configurationWithResponseContent(null)
    val response = mock[HttpServletResponse]

    new ContentCreator(configuration).createFor(response)

    verify(response, never()).getWriter
  }

  def responseWithWriter(writer: StringWriter) = {
    val response = mock[HttpServletResponse]
    when(response.getWriter).thenReturn(new PrintWriter(writer))
    response
  }

  def configurationWithResponseContent(content: String) = new HttpConfiguration(null, new Response(0, content, List()))
}
