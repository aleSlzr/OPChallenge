package com.aliaslzr.opchallenge.core.authentication.data.services

import com.aliaslzr.opchallenge.core.authentication.data.model.TokenResponse
import com.aliaslzr.opchallenge.core.authentication.data.network.AuthClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthServiceImpl
    @Inject
    constructor(
        private val authClient: AuthClient,
    ) : AuthService {
        override fun getTokenService(grantType: String, clientId: String, clientSecret: String): Flow<TokenResponse> =
            flow {
                try {
                    val response = authClient.getTokenClient(grantType, clientId, clientSecret)
                    response.body()?.let {
                        emit(it)
                    }
                } catch (e: Exception) {
                    throw e
                }
            }
}