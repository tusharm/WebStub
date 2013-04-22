package com.thoughtworks.webstub

import server.HttpServer
import utils.Client

trait StubFunctionalSpec extends SmartSpec {
  val stub: HttpServer
  val httpClient = new Client

  override protected def beforeAll() { stub.start }
  override protected def afterAll() { stub.stop }

}
