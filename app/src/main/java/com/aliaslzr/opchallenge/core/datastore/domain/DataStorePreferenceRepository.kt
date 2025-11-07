package com.aliaslzr.opchallenge.core.datastore.domain

import com.aliaslzr.opchallenge.core.authentication.domain.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface DataStorePreferenceRepository {
    fun getToken(): Flow<AuthToken>

    suspend fun setToken(token: AuthToken)
}