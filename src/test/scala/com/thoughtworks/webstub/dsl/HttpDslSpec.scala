package com.thoughtworks.webstub.dsl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.MutableList
import ResponseBuilder.response
import com.thoughtworks.webstub.dsl.HttpDsl.dslWrapped
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.{ConfigurationListener, HttpConfiguration}

@RunWith(classOf[JUnitRunner])
class HttpDslSpec extends SmartSpec {
  val configs = MutableList[HttpConfiguration]()
  val provider = dslWrapped(new ConfigurationListener {
    override def configurationCreated(configuration: HttpConfiguration) {
        configs += configuration
    }
  })

  it("should inform a consumer when configuration is created") {
    provider.get("/test").returns(response(200))
    configs should have length 1
    configs should contain(new HttpConfiguration("GET", "/test", 200))
  }

  it ("should support POST operation") {
    provider.post("/post").returns(response(202))
    configs should contain(new HttpConfiguration("POST", "/post", 202))
  }

  it ("should support PUT operation") {
    provider.put("/put").returns(response(204))
    configs should contain(new HttpConfiguration("PUT", "/put", 204))
  }

  it ("should support DELETE operation") {
    provider.delete("/delete").returns(response(404))
    configs should contain(new HttpConfiguration("DELETE", "/delete", 404))
  }

  it ("should support adding expected response content") {
    provider.get("/employee/1").returns(response(200).withContent("Bruce Willis"))
    configs should contain(new HttpConfiguration("GET", "/employee/1", 200, "Bruce Willis"))
  }

  override def afterEach() {
    configs.clear
  }
}
