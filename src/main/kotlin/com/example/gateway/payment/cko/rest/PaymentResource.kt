package com.example.gateway.payment.cko.rest;

//import com.example.gateway.payment.cko.services.AcquiringBankService
import com.example.gateway.payment.cko.domain.PaymentDetail
import com.example.gateway.payment.cko.domain.PaymentProcessRequest
import com.example.gateway.payment.cko.domain.PaymentProcessResult
import com.example.gateway.payment.cko.services.PaymentService
import io.swagger.v3.oas.annotations.Operation
import org.apache.http.HttpException
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
    ): ResponseEntity<PaymentDetail> {
        return ResponseEntity.ok(paymentService.getPaymentDetails(paymentId))
    }

    @Operation(summary = "Post request to process a payment with required data in request body")
    @PostMapping("/process")
    @ResponseStatus(ACCEPTED)
    fun requestPaymentProcess(
            @RequestBody paymentProcessRequest: PaymentProcessRequest
    ): ResponseEntity<PaymentProcessResult> {
        try {
            return ResponseEntity.ok(paymentService.sendPayment(paymentProcessRequest))
        } catch (e: HttpException) {
            throw HttpException("Failed processing payment") // TODO area to improve: provide comprehensive error message
        }
    }

}