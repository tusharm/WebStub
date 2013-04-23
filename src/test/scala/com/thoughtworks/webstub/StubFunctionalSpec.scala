package com.thoughtworks.webstub

import server.HttpServer
import utils.Client

trait StubFunctionalSpec extends SmartSpec {
  val server: HttpServer
  val httpClient = new Client

  override protected def beforeAll() { server.start }
  override protected def afterAll() { server.stop }

}
