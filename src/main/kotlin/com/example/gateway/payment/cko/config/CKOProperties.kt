package com.example.gateway.payment.cko.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties
@Configuration
class CKOProperties {
    lateinit var abcBank: ABCBankProperties
}

class ABCBankProperties {
    lateinit var url: String
}

// TODO this file is created attempting to make Wiremock work with the url specified in application.properties, but did not succeed - area to improve