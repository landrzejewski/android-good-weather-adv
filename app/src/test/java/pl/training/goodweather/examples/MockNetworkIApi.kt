package pl.training.goodweather.examples

import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            .body(gson.toJson(mockResponse.body).toResponseBody("application/json".toMediaType()))
            .build()
    }

    fun addMock(mockResponse: MockResponse) = mockResponses.add(mockResponse)

}

data class MockResponse(
    val path: String,
    val body: Any ,
    val status: Int = 200,
    val description: String = "OK",
    val delayOnMilliseconds: Long = 250,
    val isPersistent: Boolean = true
)

fun <T> createMockApi(type: Class<T>, vararg mockResponses: MockResponse, baseUrl: String = "https://localhost/"): T {
    val interceptor = MockNetworkInterceptor()
    for (mockResponse in mockResponses) {
        interceptor.addMock(mockResponse)
    }
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .addInterceptor(interceptor)
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(type)
}

