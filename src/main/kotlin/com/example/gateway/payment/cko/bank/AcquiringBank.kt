package com.example.gateway.payment.cko.bank

import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentProcessRequest
import com.example.gateway.payment.cko.domain.PaymentProcessResult

interface AcquiringBank {
    val name: String
    fun getPayment(paymentId: String): PaymentDetail
    fun sendPayment(paymentProcessRequest: PaymentProcessRequest): PaymentProcessResult
}