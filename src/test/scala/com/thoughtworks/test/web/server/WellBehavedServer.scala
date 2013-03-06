package com.thoughtworks.test.web.server

import org.scalatest.BeforeAndAfterAll

trait WellBehavedServer {
  this: BeforeAndAfterAll =>

  val server: HttpServer

  override protected def beforeAll {
    server.start
  }

  override protected def afterAll {
    server.stop
  }
}
