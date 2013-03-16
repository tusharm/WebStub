package com.thoughtworks.webstub.dsl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.MutableList
import ResponseBuilder.response
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.StubConfiguration

@RunWith(classOf[JUnitRunner])
class DslProviderSpec extends SmartSpec {
  val configs = MutableList[StubConfiguration]()
  val provider = new DslProvider() {
    protected def configurationCreated(configuration: StubConfiguration) {
      configs += configuration
    }
  }

  it("should inform a consumer when configuration is created") {
    provider.get("/test").returns(response().withStatus(204))
    configs should contain(new StubConfiguration("GET", "/test", 204))
  }

  it ("should support POST operation") {
    provider.post("/post").returns(response().withStatus(202))
    configs should have length 1
    configs should contain(new StubConfiguration("POST", "/post", 202))
  }

  override def afterEach() {
    configs.clear
  }
}
