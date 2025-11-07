package com.aliaslzr.opchallenge.core.network

import com.aliaslzr.opchallenge.BuildConfig
import com.aliaslzr.opchallenge.core.authentication.data.network.AuthClient
import com.aliaslzr.opchallenge.core.authentication.domain.model.AuthToken
import com.aliaslzr.opchallenge.core.datastore.domain.DataStorePreferenceRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor
    @Inject
    constructor(
        private val authClient: AuthClient,
        private val tokenManager: DataStorePreferenceRepository,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            runBlocking {
                val token = tokenManager.getToken().first()
                if (token.isExpired() || token.token.isNullOrEmpty()) {
                    val response = authClient.getTokenClient(
                        grantType = "grant_type",
                        clientId = BuildConfig.CLIENT_ID,
                        clientSecret = BuildConfig.CLIENT_SECRET,
                    )
                    response.body()?.let {
                        val newToken = AuthToken(
                            token = it.accessToken,
                            expiresAt = it.expiresIn.toLong(),
                        )
                        tokenManager.setToken(newToken)
                    }
                }
            }
            return chain.proceed(chain.request())
        }
    }