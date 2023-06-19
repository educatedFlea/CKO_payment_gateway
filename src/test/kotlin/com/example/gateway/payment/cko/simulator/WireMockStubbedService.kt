package com.example.gateway.payment.cko.simulator

abstract class WireMockStubbedService(private val name: String) {
    fun prefix(url: String) = "/$name$url"
}
