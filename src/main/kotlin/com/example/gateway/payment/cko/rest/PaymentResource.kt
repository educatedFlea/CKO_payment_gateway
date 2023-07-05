package com.example.gateway.payment.cko.rest;

//import com.example.gateway.payment.cko.services.AcquiringBankService
import com.example.gateway.payment.cko.domain.*
import com.example.gateway.payment.cko.services.PaymentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
public class PaymentResource(
        @Autowired private val paymentService: PaymentService,
) {

    @Operation(summary = "Returns payment details for the given payment id")
    @GetMapping("/details/payment/id/{paymentId}")
    @ResponseStatus(ACCEPTED)
    fun getPaymentDetails(
            @PathVariable paymentId: String,
    ): ResponseEntity<GetPaymentResult> {
        return ResponseEntity.ok(paymentService.getPaymentDetails(paymentId))
    }

    @Operation(summary = "Post request to process a payment with required data in request body")
    @PostMapping("/process/{paymentId}")
    @ResponseStatus(ACCEPTED)
    fun requestPaymentProcess(
            @PathVariable paymentId: String,
            @RequestBody paymentProcessRequest: PaymentProcessRequest
    ): ResponseEntity<PaymentProcessResult> {
        return ResponseEntity.ok(paymentService.sendPayment(paymentId, paymentProcessRequest))
    }
}