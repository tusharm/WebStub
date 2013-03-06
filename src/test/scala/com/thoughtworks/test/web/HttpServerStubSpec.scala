package com.thoughtworks.test.web

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.test.SmartSpec

@RunWith(classOf[JUnitRunner])
class HttpServerStubSpec extends SmartSpec {
  val serverStub = new HttpServerStub(0, "root")
}
