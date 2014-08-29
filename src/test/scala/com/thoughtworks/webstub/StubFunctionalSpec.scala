package com.thoughtworks.webstub

import com.thoughtworks.webstub.utils.Client

trait StubFunctionalSpec extends SmartSpec {
  val server: StubServer
  val httpClient = new Client

  override protected def beforeAll() { server.start }
  override protected def afterAll() { server.stop }

}
