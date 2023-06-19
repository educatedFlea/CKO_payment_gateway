package com.example.gateway.payment.cko.services

import com.example.gateway.payment.cko.bank.AcquiringBank
import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentProcessRequest
import com.example.gateway.payment.cko.domain.PaymentProcessResult
import com.example.gateway.payment.cko.domain.PaymentProcessTransaction
import com.example.gateway.payment.cko.domain.PaymentStatus.NotStarted
import com.example.gateway.payment.cko.persistence.PaymentRepository
import java.time.LocalDateTime
import java.util.*

class PaymentServiceImpl(private val paymentRepository: PaymentRepository,
                         private val validationService: PaymentValidationService,
                         private val acquiringBank: List<AcquiringBank>
) : PaymentService {
    override fun getPaymentDetails(paymentId: String): PaymentDetail {
        return paymentRepository.get(paymentId)
    }

    override fun sendPayment(request: PaymentProcessRequest): PaymentProcessResult {
        validationService.validate(request)

        val paymentId = UUID.randomUUID().toString()
        val transaction = PaymentProcessTransaction(
                paymentId = paymentId,
                paymentStatus = NotStarted,
                paymentRequest = request.copy(paymentRequestTime = LocalDateTime.now()),
        )
        paymentRepository.save(paymentId, transaction)

        val response = acquiringBank.first().sendTransaction(transaction) // TODO enhancement: to add logic to decide which banks to call, for now there's only ABCBank

        val processedTransaction = transaction.copy(
                paymentStatus = response.paymentStatus,
                paymentProcessedTime = LocalDateTime.now(),
        )
        paymentRepository.save(paymentId, processedTransaction)
        return response
    }
}