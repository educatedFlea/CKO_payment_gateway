package com.example.gateway.payment.cko.services

import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentProcessRequest
import com.example.gateway.payment.cko.domain.PaymentProcessResult

//import templates.CkoRestTemplateProvider

interface PaymentService {
    fun getPaymentDetails(paymentId: String): PaymentDetail
    fun sendPayment(request: PaymentProcessRequest): PaymentProcessResult
}