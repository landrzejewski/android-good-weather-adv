package pl.training.goodweather.examples

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createMockApi(vararg mockResponses: MockResponse, baseUrl: String = "https://localhost/"): MockUsersApi {
    val interceptor = MockNetworkInterceptor()
    for (mockResponse in mockResponses) {
        interceptor.addMock(mockResponse)
    }
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(MockUsersApi::class.java)
}