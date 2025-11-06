package com.aliaslzr.opchallenge.core.network

import com.aliaslzr.opchallenge.utils.BASE_URL
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

internal class RetrofitNetwork
    @Inject
    constructor(
        client: OkHttpClient,
        gson: Gson,
    ) {
        val provideRetrofit: Retrofit =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
    }