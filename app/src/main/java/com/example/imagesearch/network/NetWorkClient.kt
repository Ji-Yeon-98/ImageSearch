package com.example.imagesearch.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetWorkClient {

    private const val IMAGE_BASE_URL = "https://dapi.kakao.com/"

    //무조건 이렇게 사용! 복사해서 다른 곳에서도 사용
    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    private val imageRetrofit = Retrofit.Builder()
        .baseUrl(IMAGE_BASE_URL)
        //Json파일 Convert = String으로 들어온 데이터 -> Class로 바꿔줌
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()


    val ImageNetWork: NetWorkInterface = imageRetrofit.create(NetWorkInterface::class.java)

}