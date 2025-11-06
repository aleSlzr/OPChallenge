package com.aliaslzr.opchallenge.core.di.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor = Interceptor {
        val request = it.request().newBuilder()
        request.addHeader("Authorization", "iuhwefuhwoe")
        val actualRequest = request.build()
        it.proceed(actualRequest)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpBuilder =
            OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
        return httpBuilder
            .protocols(mutableListOf(Protocol.HTTP_1_1))
            .build()
    }
}