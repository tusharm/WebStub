package com.thoughtworks.webstub.dsl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.MutableList
import ResponseBuilder.response
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.StubConfiguration

@RunWith(classOf[JUnitRunner])
class DslProviderSpec extends SmartSpec {

  it("should inform a consumer when configuration is created") {
    val configs = MutableList[StubConfiguration]()

    val provider = new DslProvider() {
      protected def configurationCreated(configuration: StubConfiguration) {
        configs += configuration
      }
    }

    provider.get("/test").returns(response().withStatus(204))

    configs should contain(new StubConfiguration("GET", "/test", 204))
  }
}
