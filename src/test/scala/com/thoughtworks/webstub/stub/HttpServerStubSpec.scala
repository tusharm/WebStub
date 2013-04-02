package com.thoughtworks.webstub.stub

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.mockito.ArgumentCaptor
import org.mockito.Mockito._
import org.mockito.Matchers.{ eq  => mockitoEq }
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.{Response, Request, HttpConfiguration}
import javax.servlet.http.HttpServlet
import scala.collection.JavaConversions._

@RunWith(classOf[JUnitRunner])
class HttpServerStubSpec extends SmartSpec {
  val httpServer = mock[HttpServer]
  val serverStub = new HttpServerStub(httpServer)

  it ("should start the server") {
    serverStub.start
    verify(httpServer).start
  }

  it ("should stop the server") {
    serverStub.stop
    verify(httpServer).stop
  }
}
