package com.thoughtworks.webstub.server

import com.thoughtworks.webstub.server.context.ServletContextFactory
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec

@RunWith(classOf[JUnitRunner])
class ServletContextFactorySpec extends SmartSpec {
  it ("should create context with given context root, prefixing '/' if necessary") {
      ServletContextFactory.create("context").getContextPath should be("/context")
  }

  it ("should throw exception if the context root is null") {
    evaluating {
      ServletContextFactory.create(null);
    } should produce[IllegalArgumentException]
  }

  it ("should throw exception if the context root is empty") {
    evaluating {
      ServletContextFactory.create("");
    } should produce[IllegalArgumentException]
  }
}
