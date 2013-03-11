package com.thoughtworks.test.web.dsl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.test.SmartSpec

@RunWith(classOf[JUnitRunner])
class DslProviderSpec extends SmartSpec {

  it("should create a request with given method and uri") {
    val provider = new DslProvider(null)
    val request = provider.get("/test")

    request.method() should be("GET")
    request.uri() should be("/test")
  }
}
