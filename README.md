## CKO Payment gateway

### Core Technical Concepts/Inspiration

- This project is a prototype of two endpoints acting as payment gateway that can send payment processing request or
  retrieve processed payment details.
- There are a series of interfaces define how the gateway should behave. For example, AcquiringBank is an interface
  defines the kind of operations should to be performed and ABCBank implements the bank's specific ways to perform them.
  This allow implementations for other banks like XYZBank in the future, if the way to connect to them or the payload
  structure is different.
- The response from the aquiring bank is stubbed by Wiremock, logic captured in StubbedAcquiringBank. Whenever there are
  requests calls specified url, Wiremock will intercept and returned the stubbed response.
- Similarly, the PaymentValidationRule is an interfaces for business rule validations, currently there's 2 rules to
  validate card number length and expiry dates. More business rules can be implemented upon request.
- With the time constrain InMemoryPaymentRepository is acting as a simply structured data storage for unit testing.
- The AcquiringBankServiceTest passes as Wiremock returns stubbed response;
- PaymentServiceTest passes the test for the repository as in-memory data storage;
- When the endpoints are hit, they will return error like "payment not found" since there's no payment record stored.

### Getting Started/Requirements/Prerequisites/Dependencies

- Let gradle finish building, start up the application from main();

1. invalid payment request (card number to sort) - Send a post request http://localhost:8080/process/1234 with Body `{
   "cardNumber":"1234",
   "paymentAmount": 123,
   "expiryDate": "12-2023",
   "currency": "EUR",
   "cvv":123
   }`, a response with error will return![1.png](src%2Fmain%2Fresources%2Fpic%2F1.png)
2. save valid payment request - Send a post request http://localhost:8080/process/1234 with Body `{
   "cardNumber":"1234567812345678",
   "paymentAmount": 123,
   "expiryDate": "12-2023",
   "currency": "EUR",
   "cvv":123
   }`, a response with sccessful status will return![2.png](src%2Fmain%2Fresources%2Fpic%2F2.png)
3. Get payment with valid payment id - Send a get request http://localhost:8080/details/payment/id/1234, should get
   payment detail back![3.png](src%2Fmain%2Fresources%2Fpic%2F3.png)
4. Get payment with invalid payment id - Send a get request http://localhost:8080/details/payment/id/56789, should get
   error message "payment not found"![4.png](src%2Fmain%2Fresources%2Fpic%2F4.png)

### TODO

There are to-do comments for future enhancement in the project, summary below:

- To set up database
- The endpoint should return more structured and detailed error messages and error codes.
- Retries could be helpful.
- Request should be idempotent - if there's failure the process should picked up from where it failed last to avoid
  double-charged paymenet for example.
- More business validation rules should be added.
- To increase test coverage. 
