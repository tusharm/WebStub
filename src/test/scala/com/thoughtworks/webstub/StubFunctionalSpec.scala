package com.thoughtworks.webstub

import stub.HttpServerStub
import utils.Client

trait StubFunctionalSpec extends SmartSpec {
  val stub: HttpServerStub
  val httpClient = new Client

  override protected def beforeAll() { stub.start }
  override protected def beforeEach() { stub.reset }
  override protected def afterAll() { stub.stop }

}
