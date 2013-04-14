package com.thoughtworks.webstub.server

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.utils.Client

@RunWith(classOf[JUnitRunner])
class JettyHttpServerIntegrationSpec extends SmartSpec with WellBehavedServer {
  val server = new JettyHttpServer(9099, ServletContextFactory.create("root"))
  val httpClient = new Client

  describe("A JettyHttpServer") {
    it("should display a status page if started") {
      httpClient.get("http://localhost:9099/root/status").status should be(200)
    }

    it("should report resource not found if context doesn't exist") {
      httpClient.get("http://localhost:9099/doesNotExist/status").status should be(404)
    }

    it("should manipulate servlets on-the-fly") {
      val servlet = new StatusServlet(302)

      server.addHandlerChain("/test", servlet)
      httpClient.get("http://localhost:9099/root/test").status should be(302)

      server.removeHandlerChain("/test")
      httpClient.get("http://localhost:9099/root/test").status should be(404)

      httpClient.get("http://localhost:9099/root/status").status should be(200)
    }

    it("should register multiple paths against the same servlet") {
      val servlet = new StatusServlet(302)

      server.addHandlerChain("/test1", servlet)
      server.addHandlerChain("/test2", servlet)

      httpClient.get("http://localhost:9099/root/test1").status should be(302)
      httpClient.get("http://localhost:9099/root/test2").status should be(302)
    }
  }
}
