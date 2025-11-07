package com.aliaslzr.opchallenge.core.network

import com.aliaslzr.opchallenge.core.datastore.domain.DataStorePreferenceRepository
import com.aliaslzr.opchallenge.utils.AUTHORIZATION
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BearerInterceptor
    @Inject
    constructor(
        private val tokenManager: DataStorePreferenceRepository,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val token = runBlocking {
                tokenManager.getToken().first().token
            }
            val newRequest =
                chain
                    .request()
                    .newBuilder()
                    .addHeader(AUTHORIZATION, "Bearer $token")
                    .build()
            return chain.proceed(newRequest)
        }
    }