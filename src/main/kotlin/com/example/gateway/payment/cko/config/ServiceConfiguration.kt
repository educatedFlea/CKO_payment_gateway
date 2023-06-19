package com.example.gateway.payment.cko.config

//import com.example.gateway.payment.cko.services.AcquiringBankService
import com.example.gateway.payment.cko.bank.ABCBank
import com.example.gateway.payment.cko.bank.AcquiringBank
import com.example.gateway.payment.cko.persistence.InMemoryPaymentRepository
import com.example.gateway.payment.cko.persistence.PaymentRepository
import com.example.gateway.payment.cko.services.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//import templates.CkoRestTemplateProvider

@Configuration
//@EnableConfigurationProperties(CKOProperties::class)
class ServiceConfiguration {

    @Bean
    fun paymentValidationRules(): List<PaymentValidationRule> = listOf(
            CardNumberLengthValidation(),
            ExpirationDateValidation(),
    )

    @Bean
    fun acquiringBank(): List<AcquiringBank> = listOf(
            ABCBank()
    )

    @Bean
    fun paymentRepository(): PaymentRepository = InMemoryPaymentRepository()

    @Bean
    fun validationService(validationRules: List<PaymentValidationRule>): PaymentValidationService = PaymentValidationService(validationRules)

    @Bean
    fun paymentService(paymentRepository: PaymentRepository, validationService: PaymentValidationService, acquiringBanks: List<AcquiringBank>): PaymentService {
        return PaymentServiceImpl(paymentRepository, validationService, acquiringBanks)
    }


}