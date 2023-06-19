package com.example.gateway.payment.cko

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import simulator.StubbedAcquiringBank

@Configuration
class TestConfiguration {
    @Bean
    fun stubbedAcquiring(): StubbedAcquiringBank = StubbedAcquiringBank()

}