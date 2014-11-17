package com.thoughtworks.webstub.server

import com.thoughtworks.webstub.SmartSpec

class JettyHttpServerSpec extends SmartSpec with WellBehavedServer {
  override val server = new JettyHttpServer

  it("should start the server on a random port") {
    server.port should not be(-1)
  }
}
