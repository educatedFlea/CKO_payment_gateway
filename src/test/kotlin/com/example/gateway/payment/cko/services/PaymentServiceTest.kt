package com.example.gateway.payment.cko.services


import com.example.gateway.payment.cko.config.ServiceConfiguration
import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentStatus
import com.example.gateway.payment.cko.persistence.PaymentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

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

        val expectedPaymentDetail = PaymentDetail("1234567812345678", 123, LocalDateTime.now(), PaymentStatus.Successful)
        paymentRepository.save(paymentId, expectedPaymentDetail)

        //when
        val actualResponse = paymentService.getPaymentDetails(paymentId)

        // then
        assertThat(actualResponse).isEqualTo(expectedPaymentDetail)

    }
}