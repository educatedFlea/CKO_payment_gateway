package com.example.gateway.payment.cko.services

import com.example.gateway.payment.cko.domain.GetPaymentResult
import com.example.gateway.payment.cko.domain.PaymentProcessRequest
import com.example.gateway.payment.cko.domain.PaymentProcessResult

//import templates.CkoRestTemplateProvider

interface PaymentService {
    fun getPaymentDetails(paymentId: String): GetPaymentResult
    fun sendPayment(paymentId: String, request: PaymentProcessRequest): PaymentProcessResult
}