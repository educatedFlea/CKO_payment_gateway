package com.example.gateway.payment.cko.bank

import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentProcessResult
import com.example.gateway.payment.cko.domain.PaymentProcessTransaction

interface AcquiringBank {
    val name: String
    fun getPayment(paymentId: String): PaymentDetail
    fun sendTransaction(paymentProcessTransaction: PaymentProcessTransaction): PaymentProcessResult
}