package com.example.gateway.payment.cko.services

import com.example.gateway.payment.cko.TestConfiguration
import com.example.gateway.payment.cko.bank.AcquiringBank
import com.example.gateway.payment.cko.config.ServiceConfiguration
import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentProcessRequest

import com.example.gateway.payment.cko.domain.PaymentStatus.Successful
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import simulator.StubbedAcquiringBank
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*

@SpringBootTest(classes = [ServiceConfiguration::class, TestConfiguration::class])
@AutoConfigureWireMock(port = 6000)
class AcquiringBankServiceTest {

    @Autowired
    lateinit var stubbedAcquiringBank: StubbedAcquiringBank

    @Autowired
    lateinit var acquiringBank: List<AcquiringBank>

    private val objectMapper = ObjectMapper().registerModule(JavaTimeModule())

    @Test
    fun `finds payment details by payment id`() {
        //given
        val paymentId = "test-payment-id"
        stubbedAcquiringBank.getStubbedPaymentDetails(paymentId, objectMapper.writeValueAsString(testPaymentDetail))

        //when
        val response = acquiringBank.first().getPayment(paymentId)

        //then
        assertThat(response).isEqualTo(testPaymentDetail)
    }

    @Test
    fun `process payment with valid data successfully`() {
        //given
        val paymentId = "test-payment-id"
        stubbedAcquiringBank.stubbedPaymentProcessingFor(paymentId, Successful)

        //when
        val response = acquiringBank.first().sendPayment(paymentProcessRequest)

        //then
        assertThat(response.paymentStatus).isEqualTo(Successful)
    }

    private val paymentProcessRequest = PaymentProcessRequest(
            cardNumber = "1234567812345678",
            expiryDate = YearMonth.now(),
            paymentAmount = 50,
            currency = Currency.getInstance(Locale.FRANCE),
            cvv = 123,
    )

    private val testPaymentDetail = PaymentDetail(
            paymentId = "1234",
            paymentProcessRequest = paymentProcessRequest,
            paymentRequestTime = LocalDateTime.now(),
    )
}