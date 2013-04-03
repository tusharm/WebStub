package com.thoughtworks.webstub.dsl.builders

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.SmartSpec
import com.thoughtworks.webstub.config.{HttpConfiguration, ConfigurationProvider}
import org.mockito.Mockito._
import org.mockito.ArgumentCaptor
import ResponseBuilder.response

@RunWith(classOf[JUnitRunner])
class EntityEnclosingRequestBuilderSpec extends SmartSpec {
  it ("should take a content builder") {
    val builder = mock[ContentBuilder]
    when(builder.build()).thenReturn("content")

    val provider = mock[ConfigurationProvider]
    new EntityEnclosingRequestBuilder(provider).withContent(builder).returns(response(200))

    val captor = ArgumentCaptor.forClass(classOf[HttpConfiguration])
    verify(provider).configurationCreated(captor.capture())

    captor.getValue.request().content() should be("content")
  }
}
