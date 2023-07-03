package templates
// TODO rest templates - possible future enhancement, if it is something needs be used frequently
//
//interface CkoRestTemplate {
//    fun <R> getFromUrl(url: String, responseType: Class<R>, vararg urlVariable: Any, headers: HttpHeaders = HttpHeaders.EMPTY): R?
//    fun <T,R> postToUrl(url: String, payload: T, responseType: Class<R>, vararg urlVariable: Any, headers: HttpHeaders = HttpHeaders.EMPTY): R
//}
//interface CkoRestTemplateProvider{
//    fun get():CkoRestTemplate
//}
//class CkoRestTemplateBaseProvider(private val restTemplate: RestTemplate): CkoRestTemplateProvider {
//    override fun get(): CkoRestTemplate{
//        return CkoRestTemplateImpl(restTemplate)
//    }
//}
//
//class CkoRestTemplateImpl constructor(
//        private val restTemplate: RestTemplate
//): CkoRestTemplate {
//    override fun <R> getFromUrl(url: String, responseType: Class<R>, vararg urlVariable: Any, headers: HttpHeaders): R? {
//        val entity = HttpEntity<Void>(headers)
//        return restTemplate.exchange(url, HttpMethod.GET,entity, responseType,*urlVariable).body
//    }
//
//    override fun <T, R> postToUrl(url: String, payload: T, responseType: Class<R>, vararg urlVariable: Any, headers: HttpHeaders): R {
//        val entity = HttpEntity(payload, headers)
//        return restTemplate.exchange(url, HttpMethod.POST,entity, responseType,*urlVariable).body ?: throw HttpException() // todo more specific error messages
//    }
//}