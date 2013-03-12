package com.thoughtworks.test.web.dsl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.test.SmartSpec
import com.thoughtworks.test.web.config.StubConfiguration
import scala.collection.mutable.MutableList

@RunWith(classOf[JUnitRunner])
class DslProviderSpec extends SmartSpec {

  it("should inform a consumer when configuration is created") {
    val configs = MutableList[StubConfiguration]()

    val provider = new DslProvider() {
      protected def configurationCreated(configuration: StubConfiguration) {
        configs += configuration
      }
    }

    provider.get("/test").returns(new Response(204))

    configs should contain(new StubConfiguration("GET", "/test", 204))
  }
}
