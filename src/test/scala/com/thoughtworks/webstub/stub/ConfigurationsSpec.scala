package com.thoughtworks.webstub.stub

import config.{Configurations, MissingMatchingConfigurationException}
import matcher.RequestPartMatcher
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import scala.collection.JavaConversions._
import com.thoughtworks.webstub.config.{Response, Request, HttpConfiguration}

@RunWith(classOf[JUnitRunner])
class ConfigurationsSpec extends SmartSpec {
  val configurations = new Configurations(List(
    httpConfiguration("GET", "/test1"),
    httpConfiguration("PUT", "/test1"),
    httpConfiguration("POST", "/test2")
  ))

  it("should allow adding configuration") {
    configurations.add(httpConfiguration("DELETE", "/blah"))

    configurations.all should have size  (4)
    configurations.all should contain(httpConfiguration("DELETE", "/blah"))
  }

  it ("should filter a list of configurations based on the given matcher") {
    val filtered = configurations.filterBy(uriMatcherFor("/test1")).all

    filtered should have size  (2)
    filtered should (contain(httpConfiguration("GET", "/test1")) and contain(httpConfiguration("PUT", "/test1")))
  }

  it ("should throw an exception if no configuration is found for the given matcher") {
    evaluating {
      configurations.filterBy(uriMatcherFor("/non-existing"))
    } should produce[MissingMatchingConfigurationException]
  }

  private def uriMatcherFor(uri: String) = new RequestPartMatcher(null, 101) {
    def matches(configuration: HttpConfiguration) = configuration.request().uri().equals(uri)
  }

  private def httpConfiguration(method: String, uri: String) = new HttpConfiguration(new Request(method, uri), new Response(101, null, List()))
}
