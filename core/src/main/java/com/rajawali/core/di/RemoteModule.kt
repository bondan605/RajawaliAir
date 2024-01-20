package com.rajawali.core.di

import com.rajawali.core.data.remote.service.RajawaliAirService
import com.rajawali.core.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL + Constant.API_VERSION)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(RajawaliAirService::class.java)
    }
}