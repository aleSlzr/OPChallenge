package com.aliaslzr.opchallenge.core.di.network

import com.aliaslzr.opchallenge.core.network.RetrofitNetwork
import com.aliaslzr.opchallenge.feature.albums.data.network.AlbumListClient
import com.aliaslzr.opchallenge.feature.artists.data.network.ArtistListClient
import com.aliaslzr.opchallenge.utils.API_KEY
import com.aliaslzr.opchallenge.utils.AUTHORIZATION
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
        request.addHeader(AUTHORIZATION, API_KEY)
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

    @Singleton
    @Provides
    fun artistListClient(retrofit: RetrofitNetwork): ArtistListClient =
        retrofit.provideRetrofit.create(ArtistListClient::class.java)

    @Singleton
    @Provides
    fun albumListClient(retrofit: RetrofitNetwork): AlbumListClient =
        retrofit.provideRetrofit.create(AlbumListClient::class.java)
}