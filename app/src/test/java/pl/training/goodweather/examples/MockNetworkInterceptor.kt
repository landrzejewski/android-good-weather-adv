package pl.training.goodweather.examples

import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

class MockNetworkInterceptor: Interceptor {

    private val mockResponses = mutableListOf<MockResponse>()
    private val gson = Gson()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val mockResponse = mockResponses.find { it.path == request.url.toString() } ?: return chain.proceed(request)
        if (!mockResponse.isPersistent) {
            mockResponses.remove(mockResponse)
        }
        Thread.sleep(mockResponse.delayOnMilliseconds)
        return createResponse(mockResponse, request)
    }

    private fun createResponse(mockResponse: MockResponse, request: Request): Response {
        return Response.Builder()
            .code(mockResponse.status)
            .message(mockResponse.description)
            .protocol(Protocol.HTTP_1_1)
            .request(request)
            .body(mockResponse.body.invoke().toResponseBody("application/json".toMediaType()))
            .build()
    }

    fun addMock(mockResponse: MockResponse) = mockResponses.add(mockResponse)

}

data class MockResponse(
    val path: String,
    val body: () -> String,
    val status: Int,
    val description: String,
    val delayOnMilliseconds: Long,
    val isPersistent: Boolean
)