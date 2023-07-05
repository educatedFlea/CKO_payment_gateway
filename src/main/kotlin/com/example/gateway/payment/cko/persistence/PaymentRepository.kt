package com.example.gateway.payment.cko.persistence

import com.example.gateway.payment.cko.domain.PaymentDetail

interface PaymentRepository {
    fun save(paymentId: String, paymentDetail: PaymentDetail)

    fun get(paymentId: String): PaymentDetail
}