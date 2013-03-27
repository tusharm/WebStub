package com.thoughtworks.webstub.server.utils

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.eclipse.jetty.servlet.{ServletHolder, ServletMapping, ServletHandler}
import javax.servlet.http.HttpServlet
import org.mockito.Mockito._
import org.mockito.Matchers._
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.server.ServletContextHandler

@RunWith(classOf[JUnitRunner])
class JettyServletRemoverSpec extends SmartSpec {
  var servletHandler: ServletHandler = _
  var remover: JettyServletRemover = _

  override protected def beforeEach() {
    servletHandler = mock[ServletHandler]

    val contextHandler = mock[ServletContextHandler]
    when(contextHandler.getServletHandler).thenReturn(servletHandler)

    remover = new JettyServletRemover(contextHandler)
  }

  it("should remove servlet with the given path") {
    when(servletHandler.getServletMappings).thenReturn(Array(servletMapping("/test", "blah")))
    when(servletHandler.getServlets).thenReturn(Array(servletHolder("blah")))

    remover.remove("/test")

    verify(servletHandler).setServletMappings(Array())
    verify(servletHandler).setServlets(Array())
  }

  it("should have no effect if servlet not found") {
    when(servletHandler.getServletMappings).thenReturn(Array(servletMapping("/test", "blah")))
    when(servletHandler.getServlets).thenReturn(Array(servletHolder("blah")))

    remover.remove("non-existent")

    verify(servletHandler, never()).setServletMappings(any())
    verify(servletHandler, never()).setServlets(any())
  }

  it ("should have not effect if no servlets exist") {
    when(servletHandler.getServletMappings).thenReturn(null)

    remover.remove("non-existent")

    verify(servletHandler, never()).setServletMappings(any())
    verify(servletHandler, never()).setServlets(any())
  }

  it("should remove all servlets with the given path") {
    val mapping = servletMapping("/test1", "three")
    val holder = servletHolder("three")

    when(servletHandler.getServletMappings).thenReturn(Array(servletMapping("/test", "one"), servletMapping("/test", "two"), mapping))
    when(servletHandler.getServlets).thenReturn(Array(servletHolder("one"), servletHolder("two"), holder))

    remover.remove("/test")

    verify(servletHandler).setServletMappings(Array(mapping))
    verify(servletHandler).setServlets(Array(holder))
  }

  private def servletMapping(servletPath: String, servletName: String) = {
    val mapping = new ServletMapping()
    mapping.setPathSpec(servletPath)
    mapping.setServletName(servletName)
    mapping
  }

  private def servletHolder(servletName: String) = new ServletHolder(servletName, classOf[HttpServlet])
}
