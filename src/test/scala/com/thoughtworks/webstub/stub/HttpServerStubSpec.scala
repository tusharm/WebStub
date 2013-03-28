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

  it ("should update server when configuration is created") {
    val configuration = httpConfiguration("GET", "/test", 200)
    serverStub.configurationCreated(configuration)

    val captor = ArgumentCaptor.forClass(classOf[HttpServlet])
    verify(httpServer).addHandlerChain(mockitoEq("/test"), captor.capture())

    val servlet = captor.getValue.asInstanceOf[StubServlet]
    servlet.getConfiguration should be(configuration)
  }

  it("should reset all configurations") {
    serverStub.configurationCreated(httpConfiguration("POST", "/person/1", 202))
    serverStub.configurationCreated(httpConfiguration("POST", "/person/2", 202))

    serverStub.reset

    verify(httpServer).removeHandlerChain("/person/1")
    verify(httpServer).removeHandlerChain("/person/2")

    serverStub.registeredUris should have size 0
  }

  private def httpConfiguration(method: String, uri: String, status: Int, responseContent: String = null) =
    new HttpConfiguration(new Request(method, uri), new Response(status, responseContent, List()))
}
