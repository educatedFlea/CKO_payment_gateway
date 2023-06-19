package com.example.gateway.payment.cko.persistence

import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentProcessTransaction

class InMemoryPaymentRepository : PaymentRepository {

    private val store = mutableMapOf<String, MutableList<PaymentDetail>>()
    private val transactionStore = mutableMapOf<String, MutableList<PaymentProcessTransaction>>()

    override fun save(paymentId: String, paymentDetail: PaymentDetail) {
        if (!store.containsKey(paymentId)) {
            store[paymentId] = mutableListOf()
        }
        store[paymentId]!!.add(paymentDetail)
    }

    override fun save(paymentId: String, paymentProcessTransaction: PaymentProcessTransaction) {
        if (!transactionStore.containsKey(paymentId)) {
            transactionStore[paymentId] = mutableListOf()
        }
        transactionStore[paymentId]!!.add(paymentProcessTransaction)
    }

    override fun get(paymentId: String): PaymentDetail {
        return store[paymentId]?.last() ?: throw IllegalStateException("payment not found")
    }
}