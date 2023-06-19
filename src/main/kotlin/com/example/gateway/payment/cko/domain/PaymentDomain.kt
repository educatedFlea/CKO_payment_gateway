package com.example.gateway.payment.cko.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*

data class PaymentDetail(
        @JsonProperty("cardNumber")
        val cardNumber: String,
        @JsonProperty("paymentAmount")
        val paymentAmount: Int,
        @JsonProperty("paymentProcessedTime")
        val paymentProcessedTime: LocalDateTime,
        @JsonProperty("paymentStatus")
        val paymentStatus: PaymentStatus,
)

data class PaymentProcessRequest(
        @JsonProperty("cardNumber")
        val cardNumber: String,
        @JsonProperty("paymentAmount")
        val paymentAmount: Int,
        @JsonProperty("expiryDate")
        val expiryDate: YearMonth,
        @JsonProperty("paymentRequestTime")
        val paymentRequestTime: LocalDateTime,
        @JsonProperty("currency")
        val currency: Currency,
        @JsonProperty("cvv")
        val cvv: Int,
)

enum class PaymentStatus {
    Successful,
    Failed,
    Pending,
    InProgress,
    NotStarted
}

data class PaymentProcessResult(
        @JsonProperty("paymentId")
        val paymentId: String,
        @JsonProperty("paymentStatus")
        val paymentStatus: PaymentStatus,
)

data class PaymentProcessTransaction(
        @JsonProperty("paymentId")
        val paymentId: String,
        @JsonProperty("paymentStatus")
        val paymentStatus: PaymentStatus,
        @JsonProperty("paymentRequest")
        val paymentRequest: PaymentProcessRequest,
        @JsonProperty("paymentProcessedTime")
        val paymentProcessedTime: LocalDateTime? = null,
)