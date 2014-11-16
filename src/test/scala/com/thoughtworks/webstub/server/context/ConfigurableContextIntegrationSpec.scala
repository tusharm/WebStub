package com.thoughtworks.webstub.server.context

import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.{Header, Response, Request, HttpConfiguration}
import com.thoughtworks.webstub.server.{JettyHttpServer, HttpServer, WellBehavedServer}
import com.thoughtworks.webstub.utils.Client
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import scala.collection.JavaConversions

@RunWith(classOf[JUnitRunner])
class ConfigurableContextIntegrationSpec extends SmartSpec with WellBehavedServer {
  override val server = new JettyHttpServer(9099)
  val httpClient = new Client

  describe("A configurable context") {
    val context = new ConfigurableContext(server, "root")

    it("should not allow a null context root") {
      evaluating { new ConfigurableContext(server, null) } should produce[IllegalArgumentException]
    }

    it("should not allow an empty context root") {
      evaluating { new ConfigurableContext(server, "") } should produce[IllegalArgumentException]
    }

    it("should have a status page") {
      httpClient.get("http://localhost:9099/root/__status__").status should be(200)
    }

    it("should report a non-existing context") {
      httpClient.get("http://localhost:9099/doesNotExist/__status__").status should be(404)
    }

    describe("has a configurable servlet, that") {
      val request = new Request("GET", "/hello")
      val response = new Response(200, "hello", JavaConversions.asJavaCollection(List()))

      it("should add new configuration") {
        httpClient.get("http://localhost:9099/root/").status should be(501)

        context.configurationCreated(new HttpConfiguration(request, response))

        httpClient.get("http://localhost:9099/root/hello").status should be(200)
      }

      it("should clear existing configuration") {
        context.configurationCreated(new HttpConfiguration(request, response))
        context.configurationCleared

        httpClient.get("http://localhost:9099/root/hello").status should be(501)
      }
    }
  }
}
