package com.thoughtworks.webstub

import dsl.builders.ResponseBuilder.response
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import StubServer.newServer

@RunWith(classOf[JUnitRunner])
class MultipleContextStubbingSpec extends StubFunctionalSpec {
  val server = newServer(9009)

  it ("should allow stubbing multiple contexts on the same port") {
    val stubEmployees = server.stub("employees")
    stubEmployees.get("/1").returns(response(200).withContent("1st employee"))

    val stubAccounts = server.stub("accounts")
    stubAccounts.get("/employees/1").returns(response(403))

    httpClient.get("http://localhost:9009/employees/1").status should be(200)
    httpClient.get("http://localhost:9009/accounts/employees/1").status should be(403)
  }
}
