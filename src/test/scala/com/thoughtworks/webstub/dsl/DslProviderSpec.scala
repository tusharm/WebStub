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
    provider.get("/test").returns(response().withStatus(200))
    configs should have length 1
    configs should contain(new StubConfiguration("GET", "/test", 200))
  }

  it ("should support POST operation") {
    provider.post("/post").returns(response().withStatus(202))
    configs should contain(new StubConfiguration("POST", "/post", 202))
  }

  it ("should support PUT operation") {
    provider.put("/put").returns(response().withStatus(204))
    configs should contain(new StubConfiguration("PUT", "/put", 204))
  }

  it ("should support DELETE operation") {
    provider.delete("/delete").returns(response().withStatus(404))
    configs should contain(new StubConfiguration("DELETE", "/delete", 404))
  }

  override def afterEach() {
    configs.clear
  }
}
