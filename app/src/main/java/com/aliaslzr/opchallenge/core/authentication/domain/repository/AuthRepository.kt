package com.aliaslzr.opchallenge.core.authentication.domain.repository

import com.aliaslzr.opchallenge.core.authentication.domain.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val getTokenFromDataStore: Flow<AuthToken>

    suspend fun getValidToken(): AuthToken
}