package com.thoughtworks.test.web.dsl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.test.SmartSpec
import org.mockito.Mockito._
import com.thoughtworks.test.web.config.StubConfiguration

@RunWith(classOf[JUnitRunner])
class DslProviderSpec extends SmartSpec {

  it("should inform a consumer when configuration is created") {
    val consumer = mock[DslClient]
    val provider = new DslProvider(consumer)

    provider.get("/test").returns(new Response(204))

    verify(consumer).configurationCreated(new StubConfiguration("GET", "/test", 204))
  }
}
