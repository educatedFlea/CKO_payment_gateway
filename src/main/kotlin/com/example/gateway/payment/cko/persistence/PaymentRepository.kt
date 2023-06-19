package com.example.gateway.payment.cko.persistence

import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentProcessTransaction

interface PaymentRepository {
    fun save(paymentId: String, paymentDetail: PaymentDetail)
    fun save(paymentId: String, paymentProcessTransaction: PaymentProcessTransaction)

    fun get(paymentId: String): PaymentDetail
}