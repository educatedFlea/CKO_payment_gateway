package com.example.gateway.payment.cko.services

import com.example.gateway.payment.cko.bank.AcquiringBank
import com.example.gateway.payment.cko.domain.GetPaymentResult
import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentProcessRequest
import com.example.gateway.payment.cko.domain.PaymentProcessResult
import com.example.gateway.payment.cko.domain.PaymentStatus.*
import com.example.gateway.payment.cko.persistence.PaymentRepository
import java.time.LocalDateTime

class PaymentServiceImpl(private val paymentRepository: PaymentRepository,
                         private val validationService: PaymentValidationService,
                         private val acquiringBank: List<AcquiringBank>
) : PaymentService {
    override fun getPaymentDetails(paymentId: String): GetPaymentResult {

        try {
            val paymentDetail = paymentRepository.get(paymentId)
            return GetPaymentResult(paymentDetail)
        } catch (e: Exception) {
            return GetPaymentResult(errorMessage = e.message)
        }
    }

    /**
     * ideally the payment id shouldn't be passed in, it should be populated as part of the payment detail as originally designed
     * it's a temporary workaround for the InMemoryPaymentRepository to get payment detail
     */
    override fun sendPayment(paymentId: String, request: PaymentProcessRequest): PaymentProcessResult {
        try {
            validationService.validate(request)
        } catch (e: Exception) {
            return PaymentProcessResult(paymentId, Invalid, e.message)
        }
        val paymentDetail = PaymentDetail(
                paymentId = paymentId,
                paymentProcessRequest = request,
                paymentRequestTime = LocalDateTime.now(),
                paymentStatus = Validated,

                )
        paymentRepository.save(paymentId, paymentDetail)
        /**
         * TODO requires a response for this logic to work...
         * to explain logic:
         * call a bank and get the response
         * according to the status in response, populate the paymentDetail object with the additional information like payment processed time and payment status
         * save the updated paymentDetail
         */
//        val response = acquiringBank.first().sendPayment(request) // TODO enhancement: to add logic to decide which banks to call, for now there's only ABCBank
//
//        if (response.paymentStatus == Successful){
//            val processedPaymentDetail = paymentDetail.copy(
//                    paymentStatus = response.paymentStatus,
//                    paymentProcessedTime = LocalDateTime.now(),
//            )
//            paymentRepository.save(paymentId,processedPaymentDetail)
//        } else{
//            val processedPaymentDetail = paymentDetail.copy(
//                    paymentStatus = Failed,
//                    paymentProcessedTime = LocalDateTime.now(),
//            )
//            paymentRepository.save(paymentId, processedPaymentDetail)
//        }
        return PaymentProcessResult(paymentId, Successful)
    }
}