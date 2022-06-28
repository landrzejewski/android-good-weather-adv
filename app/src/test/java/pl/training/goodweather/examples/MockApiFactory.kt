package pl.training.goodweather.examples

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun <T> createMockApi(vararg mockResponses: MockResponse, type: Class<T>, baseUrl: String = "https://localhost/"): T {
    val interceptor = MockNetworkInterceptor()
    for (mockResponse in mockResponses) {
        interceptor.addMock(mockResponse)
    }
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(BASIC))
        .addInterceptor(interceptor)
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(type)
}