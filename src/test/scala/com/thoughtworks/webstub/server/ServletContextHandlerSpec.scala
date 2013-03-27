package com.thoughtworks.webstub.server

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import org.mockito.Mockito._
import utils.JettyServletRemover

@RunWith(classOf[JUnitRunner])
class ServletContextHandlerSpec extends SmartSpec {

  it ("should allow removing servlets") {
    val remover = mock[JettyServletRemover]
    val handler = new ServletContextHandler(remover, "/")

    handler.removeServlet("/path")
    verify(remover).remove("/path")
  }
}
