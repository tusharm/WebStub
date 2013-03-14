package com.thoughtworks.test.web.stub

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.test.SmartSpec
import com.thoughtworks.test.web.config.StubConfiguration
import org.mockito.ArgumentCaptor
import org.mockito.Mockito._
import org.mockito.Matchers.{ eq  => mockitoEq }
import javax.servlet.Servlet

@RunWith(classOf[JUnitRunner])
class HttpServerStubSpec extends SmartSpec {
  val httpServer = mock[HttpServer]
  val serverStub = new HttpServerStub(httpServer)

  it ("should update server when configuration is created") {
    val configuration = new StubConfiguration("GET", "/test", 200)
    serverStub.configurationCreated(configuration)

    val captor = ArgumentCaptor.forClass(classOf[Servlet])
    verify(httpServer).addServlet(captor.capture, mockitoEq("/test"))

    val servlet = captor.getValue.asInstanceOf[StubServlet]
    servlet should not(be(null))
    servlet.getConfiguration should be(configuration)
  }
}
