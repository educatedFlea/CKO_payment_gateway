package com.example.gateway.payment.cko.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*

data class PaymentDetail(
        @JsonProperty("paymentId")
        val paymentId: String,
        @JsonProperty("paymentProcessRequest")
        val paymentProcessRequest: PaymentProcessRequest,
        @JsonProperty("paymentStatus")
        val paymentStatus: PaymentStatus? = null,
        @JsonProperty("paymentRequestTime")
        val paymentRequestTime: LocalDateTime? = null,
        @JsonProperty("paymentProcessedTime")
        val paymentProcessedTime: LocalDateTime? = null,
)

data class PaymentProcessRequest(
        @JsonProperty("cardNumber")
        val cardNumber: String,
        @JsonProperty("paymentAmount")
        val paymentAmount: Int,
        @JsonProperty("expiryDate")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yyyy")
        val expiryDate: YearMonth,
        @JsonProperty("currency")
        val currency: Currency,
        @JsonProperty("cvv")
        val cvv: Int,
)
enum class PaymentStatus {
    Successful,
    Validated,
    Invalid,
    Failed,
    Pending,
    InProgress,
}

data class PaymentProcessResult(
        @JsonProperty("paymentId")
        val paymentId: String,
        @JsonProperty("paymentStatus")
        val paymentStatus: PaymentStatus,
        @JsonProperty("errorMessage")
        val errorMessage: String? = null,
)

data class GetPaymentResult(
        @JsonProperty("paymentDetail")
        val paymentDetail: PaymentDetail? = null,
        @JsonProperty("errorMessage")
        val errorMessage: String? = null,
)

data class ValidationResult(
        val isValid: Boolean,
        val errorMessage: String? = null,
)