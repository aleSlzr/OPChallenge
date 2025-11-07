package com.aliaslzr.opchallenge.core.network

import com.aliaslzr.opchallenge.core.di.network.AuthClientQualifier
import com.aliaslzr.opchallenge.core.di.network.MainClientQualifier
import com.aliaslzr.opchallenge.utils.AUTH_BASE_URL
import com.aliaslzr.opchallenge.utils.MAIN_BASE_URL
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

internal class RetrofitNetwork
    @Inject
    constructor(
        @MainClientQualifier mainClient: OkHttpClient,
        @AuthClientQualifier authClient: OkHttpClient,
        gson: Gson,
    ) {
        val provideMainRetrofit: Retrofit =
            Retrofit
                .Builder()
                .baseUrl(MAIN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mainClient)
                .build()

        val provideAuthRetrofit: Retrofit =
            Retrofit
                .Builder()
                .baseUrl(AUTH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authClient)
                .build()
    }