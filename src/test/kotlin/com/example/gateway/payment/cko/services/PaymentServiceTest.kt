package com.example.gateway.payment.cko.services


import com.example.gateway.payment.cko.config.ServiceConfiguration
import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentProcessRequest
import com.example.gateway.payment.cko.persistence.PaymentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.YearMonth
import java.util.*

@SpringBootTest(classes = [ServiceConfiguration::class])
class PaymentServiceTest {
    @Autowired
    lateinit var paymentService: PaymentService

    @Autowired
    lateinit var paymentRepository: PaymentRepository

    @Test
    fun `get the payment detail for a given payment id`() {
        //given
        val paymentId = "1234"

        val expectedPaymentDetail = PaymentDetail(
                paymentId = "1234",
                paymentProcessRequest = PaymentProcessRequest(
                        cardNumber = "1234567812345678",
                        paymentAmount = 100,
                        expiryDate = YearMonth.now(),
                        currency = Currency.getInstance(Locale.FRANCE),
                        cvv = 123,
                )
        )
        paymentRepository.save(paymentId, expectedPaymentDetail)

        //when
        val actualResponse = paymentService.getPaymentDetails(paymentId).paymentDetail

        // then
        assertThat(actualResponse).isEqualTo(expectedPaymentDetail)
    }

    @Test
    fun `save the payment detail successfully`() {
        //given
        val paymentId = "5678"

        val requestToBeSaved = PaymentProcessRequest(
                cardNumber = "1234567812345678",
                expiryDate = YearMonth.of(2025, 12),
                paymentAmount = 1000,
                currency = Currency.getInstance(Locale.UK),
                cvv = 123,
        )

        //when
        paymentService.sendPayment(paymentId, requestToBeSaved)
        val savedPaymentDetail = paymentService.getPaymentDetails(paymentId).paymentDetail!!.paymentProcessRequest

        // then
        assertThat(requestToBeSaved).isEqualTo(savedPaymentDetail)
    }
}