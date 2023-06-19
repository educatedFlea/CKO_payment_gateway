package com.example.gateway.payment.cko.services

import com.example.gateway.payment.cko.domain.PaymentProcessRequest
import java.time.YearMonth

class PaymentValidationService(private val validationRules: List<PaymentValidationRule>) {
    fun validate(request: PaymentProcessRequest) {
        if (!validationRules.all { it.validate(request) }) {
            throw IllegalArgumentException("Validation failed")
        }
    }
}

/**
 * TODO Enhancement: This interface can be implemented by more validations rules
 */
interface PaymentValidationRule {
    fun validate(paymentProcessRequest: PaymentProcessRequest): Boolean
}

class CardNumberLengthValidation : PaymentValidationRule {
    override fun validate(paymentProcessRequest: PaymentProcessRequest): Boolean {
        return (paymentProcessRequest.cardNumber.length in 16..17)
    }
}

class ExpirationDateValidation : PaymentValidationRule {
    override fun validate(paymentProcessRequest: PaymentProcessRequest): Boolean {
        return (paymentProcessRequest.expiryDate.isAfter(YearMonth.now()))
    }
}