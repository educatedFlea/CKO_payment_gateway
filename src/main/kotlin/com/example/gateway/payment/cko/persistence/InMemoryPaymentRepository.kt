package com.example.gateway.payment.cko.persistence

import com.example.gateway.payment.cko.domain.PaymentDetail

class InMemoryPaymentRepository : PaymentRepository {

    private val store = mutableMapOf<String, MutableList<PaymentDetail>>()

    override fun save(paymentId: String, paymentDetail: PaymentDetail) {
        if (!store.containsKey(paymentId)) {
            store[paymentId] = mutableListOf()
        }
        store[paymentId]!!.add(paymentDetail)
    }

    override fun get(paymentId: String): PaymentDetail {
        return store[paymentId]?.last() ?: throw IllegalStateException("payment not found")
    }
}