package com.aliaslzr.opchallenge.core.di.network

import com.aliaslzr.opchallenge.core.authentication.data.network.AuthClient
import com.aliaslzr.opchallenge.core.network.BearerInterceptor
import com.aliaslzr.opchallenge.core.network.RetrofitNetwork
import com.aliaslzr.opchallenge.feature.albums.data.network.AlbumListClient
import com.aliaslzr.opchallenge.feature.artists.data.network.ArtistListClient
import com.aliaslzr.opchallenge.feature.tracks.data.network.TrackListClient
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
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthClientQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainClientQualifier

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

    /*
    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor = Interceptor {
        val request = it.request().newBuilder()
        request.addHeader(AUTHORIZATION, API_KEY)
        val actualRequest = request.build()
        it.proceed(actualRequest)
    }
    */

    /*
    @Provides
    @Singleton
    fun provideInterceptor(
        tokenManager: DataStorePreferenceRepository,
    ): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = runBlocking {
            tokenManager.getToken()
        }
        val requestBuilder = originalRequest
            .newBuilder()
            .header(AUTHORIZATION, "Bearer $token")
        val actualRequest = requestBuilder.build()
        chain.proceed(actualRequest)
    }
    */

    @AuthClientQualifier
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @MainClientQualifier
    @Provides
    @Singleton
    fun provideApiOkHttpClient(
        bearerInterceptor: BearerInterceptor,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(bearerInterceptor)
            .build()

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
        retrofit.provideMainRetrofit.create(ArtistListClient::class.java)

    @Singleton
    @Provides
    fun albumListClient(retrofit: RetrofitNetwork): AlbumListClient =
        retrofit.provideMainRetrofit.create(AlbumListClient::class.java)

    @Singleton
    @Provides
    fun trackListClient(retrofit: RetrofitNetwork): TrackListClient =
        retrofit.provideMainRetrofit.create(TrackListClient::class.java)

    @Singleton
    @Provides
    fun authClient(retrofit: RetrofitNetwork): AuthClient =
        retrofit.provideAuthRetrofit.create(AuthClient::class.java)
}