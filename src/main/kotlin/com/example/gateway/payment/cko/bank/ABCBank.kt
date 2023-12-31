package com.example.gateway.payment.cko.bank

import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentProcessRequest
import com.example.gateway.payment.cko.domain.PaymentProcessResult
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

@Component
class ABCBank() : AcquiringBank {
    private val objectMapper = ObjectMapper().registerModule(JavaTimeModule())
    override val name: String = "ABC"
// TODO - see comments in CKOProperties
//    @Value("\${abcbank.url}")
//    lateinit var url: String
override fun getPayment(paymentId: String): PaymentDetail {
    val client = HttpClient.newBuilder().build();
    val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:6000/ABCBank/payment/id/${paymentId}")) //  hardcoded localhost port for temporary wiremock test workaround TODO area to improve
            .build();
    val response = client.send(request, BodyHandlers.ofString());
    return objectMapper.readValue(response.body(), PaymentDetail::class.java)

}

    override fun sendPayment(paymentProcessRequest: PaymentProcessRequest): PaymentProcessResult {
        val requestBody = objectMapper.writeValueAsString(paymentProcessRequest)

        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:6000/ABCBank/payment/process")) //  hardcoded localhost port for temporary wiremock test workaround TODO area to improve
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build()
        val response = client.send(request, BodyHandlers.ofString());
        val responseBody = response.body()
        return objectMapper.readValue(responseBody, PaymentProcessResult::class.java)
    }

}