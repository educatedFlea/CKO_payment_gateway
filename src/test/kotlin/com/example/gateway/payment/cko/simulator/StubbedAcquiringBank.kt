package simulator

import com.example.gateway.payment.cko.domain.PaymentProcessResult
import com.example.gateway.payment.cko.domain.PaymentStatus
import com.example.gateway.payment.cko.simulator.WireMockStubbedService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.apache.hc.core5.http.HttpStatus.SC_OK
import org.springframework.stereotype.Component


@Component
class StubbedAcquiringBank : WireMockStubbedService("acquiringBank") {
    private val objectMapper = ObjectMapper().registerModule(JavaTimeModule())
    fun getStubbedPaymentDetails(paymentId: String, paymentDetailAsString: String) {
        stubFor(get(urlMatching("/ABCBank/payment/id/${paymentId}"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(SC_OK)
                        .withBody(paymentDetailAsString)
                )
        )
    }

    fun stubbedPaymentProcessingFor(paymentId: String, paymentStatus: PaymentStatus) {
        stubFor(post(urlEqualTo("/ABCBank/payment/process"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(SC_OK)
                        .withBody(objectMapper.writeValueAsString(PaymentProcessResult(paymentId = paymentId, paymentStatus)))
                )
        )
    }


}