package com.thoughtworks.webstub.server.utils

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import com.thoughtworks.webstub.SmartSpec
import org.eclipse.jetty.servlet._
import org.mockito.Mockito._
import org.mockito.Matchers._

@RunWith(classOf[JUnitRunner])
class JettyFilterRemoverSpec extends SmartSpec {
  var servletHandler: ServletHandler = _
  var remover: JettyFilterRemover = _

  override protected def beforeEach() {
    servletHandler = mock[ServletHandler]
    when(servletHandler.getFilterMappings).thenReturn(Array(filterMapping("/test", "blah")))
    when(servletHandler.getFilters).thenReturn(Array(filterHolder("blah")))

    val contextHandler = mock[ServletContextHandler]
    when(contextHandler.getServletHandler).thenReturn(servletHandler)

    remover = new JettyFilterRemover(contextHandler)
  }

  it("should remove filter with the given path") {
    remover.remove("/test")

    verify(servletHandler).setFilterMappings(Array())
    verify(servletHandler).setFilters(Array())
  }

  it("should have no effect if filter not found") {
    remover.remove("non-existent")

    verify(servletHandler, never()).setFilterMappings(any())
    verify(servletHandler, never()).setFilters(any())
  }

  private def filterMapping(pathSpec: String, filterName: String) = {
    val mapping = new FilterMapping
    mapping.setPathSpec(pathSpec)
    mapping.setFilterName(filterName)
    mapping
  }

  private def filterHolder(filterName: String) = {
    val holder = new FilterHolder
    holder.setName(filterName)
    holder
  }
}
