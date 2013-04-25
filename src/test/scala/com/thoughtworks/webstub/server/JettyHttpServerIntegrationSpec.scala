package com.thoughtworks.webstub.server

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.utils.Client

@RunWith(classOf[JUnitRunner])
class JettyHttpServerIntegrationSpec extends SmartSpec with WellBehavedServer {
  val server = new JettyHttpServer(9099)
  val context = ServletContextFactory.create("root")
  server.addContext(context)

  val httpClient = new Client

  describe("A JettyHttpServer") {
    it("should display a status page if started") {
      httpClient.get("http://localhost:9099/root/__status__").status should be(200)
    }

    it("should report resource not found if context doesn't exist") {
      httpClient.get("http://localhost:9099/doesNotExist/__status__").status should be(404)
    }

    it("should manipulate servlets on-the-fly") {
      val servlet = new StatusServlet(302)

      context.addServlet("/test", servlet)
      httpClient.get("http://localhost:9099/root/test").status should be(302)

      context.removeServlet("/test")
      httpClient.get("http://localhost:9099/root/test").status should be(404)

      httpClient.get("http://localhost:9099/root/__status__").status should be(200)
    }

    it("should register multiple paths against the same servlet") {
      val servlet = new StatusServlet(302)

      context.addServlet("/test1", servlet)
      context.addServlet("/test2", servlet)

      httpClient.get("http://localhost:9099/root/test1").status should be(302)
      httpClient.get("http://localhost:9099/root/test2").status should be(302)
    }

    it ("should be able to add contexts dynamically") {
      val server = new JettyHttpServer(9999)
      server.start

      server.addContext(ServletContextFactory.create("context"))
      httpClient.get("http://localhost:9999/context/__status__").status should be(200)

      server.stop
    }
  }
}
