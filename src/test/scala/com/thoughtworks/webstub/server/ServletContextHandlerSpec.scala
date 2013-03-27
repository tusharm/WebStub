package com.thoughtworks.webstub.server

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class ServletContextHandlerSpec extends SmartSpec {

  it ("should allow removing servlets") {
    val servletRemover = mock[JettyServletRemover]
    val handler = new ServletContextHandler(servletRemover, "/")

    handler.removeServlet("/path")
    verify(servletRemover).remove("/path")
  }
}
