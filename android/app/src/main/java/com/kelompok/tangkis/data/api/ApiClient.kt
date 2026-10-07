package com.kelompok.tangkis.data.api

import com.kelompok.tangkis.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = BuildConfig.API_BASE_URL

    val service: TangkisApiService by lazy {
        require(BASE_URL.endsWith("/")) { "API_BASE_URL harus diakhiri '/'." }

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TangkisApiService::class.java)
    }
}
