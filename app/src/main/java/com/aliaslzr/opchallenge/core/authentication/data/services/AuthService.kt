package com.aliaslzr.opchallenge.core.authentication.data.services

import com.aliaslzr.opchallenge.core.authentication.data.model.TokenResponse
import kotlinx.coroutines.flow.Flow

interface AuthService {
    fun getTokenService(grantType: String, clientId: String, clientSecret: String): Flow<TokenResponse>
}