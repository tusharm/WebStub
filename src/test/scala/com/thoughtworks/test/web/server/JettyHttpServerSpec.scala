package com.thoughtworks.test.web.server

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.test.SmartSpec
import com.thoughtworks.test.web.utils.Client

@RunWith(classOf[JUnitRunner])
class JettyHttpServerSpec extends SmartSpec with WellBehavedServer {
  val server = new JettyHttpServer(9099, "root")
  val httpClient = new Client

  describe("An JettyHttpServer") {
    it("should display a status page if started") {
      httpClient.get("http://localhost:9099/root/status").status should be(200)
    }

    it("must not accept an invalid context root") {
      evaluating(new JettyHttpServer(9099, null)) should produce[IllegalArgumentException]
    }

    it("should report resource not found if context doesn't exist") {
      httpClient.get("http://localhost:9099/doesNotExist/status").status should be(404)
    }

    it("should manipulate servlets on-the-fly") {
      val servlet = new StatusServlet(302)

      server.addServlet(servlet, "/test")
      httpClient.get("http://localhost:9099/root/test").status should be(302)

      server.removeServlet("/test")
      httpClient.get("http://localhost:9099/root/test").status should be(404)

      httpClient.get("http://localhost:9099/root/status").status should be(200)
    }
  }
}
