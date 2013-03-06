package com.thoughtworks.test

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSpec}
import org.scalatest.matchers.ShouldMatchers

trait SmartSpec extends FunSpec
  with ShouldMatchers
  with BeforeAndAfterEach
  with BeforeAndAfterAll
