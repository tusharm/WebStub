package com.thoughtworks.test.web.server

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.test.SmartSpec
import org.eclipse.jetty.servlet.{ServletHolder, ServletMapping, ServletHandler, ServletContextHandler}
import javax.servlet.http.HttpServlet
import org.mockito.Mockito._
import org.mockito.Matchers._

@RunWith(classOf[JUnitRunner])
class JettyServletRemoverSpec extends SmartSpec {
  var servletHandler: ServletHandler = _
  var remover: JettyServletRemover = _

  override protected def beforeEach() {
    servletHandler = mock[ServletHandler]
    when(servletHandler.getServletMappings).thenReturn(Array(servletMapping("/test", "blah")))
    when(servletHandler.getServlets).thenReturn(Array(servletHolder("blah")))

    val contextHandler = mock[ServletContextHandler]
    when(contextHandler.getServletHandler).thenReturn(servletHandler)

    remover = new JettyServletRemover(contextHandler)
  }

  it("should remove servlet with the given path") {
    remover.remove("/test")

    verify(servletHandler).setServletMappings(Array())
    verify(servletHandler).setServlets(Array())
  }

  it("should have no effect if servlet not found") {
    remover.remove("non-existent")

    verify(servletHandler, never()).setServletMappings(any())
    verify(servletHandler, never()).setServlets(any())
  }

  private def servletMapping(servletPath: String, servletName: String) = {
    val mapping = new ServletMapping()
    mapping.setPathSpec(servletPath)
    mapping.setServletName(servletName)
    mapping
  }

  private def servletHolder(servletName: String) = new ServletHolder(servletName, classOf[HttpServlet])
}
