package com.aliaslzr.opchallenge.core.authentication.data.repository

import com.aliaslzr.opchallenge.BuildConfig
import com.aliaslzr.opchallenge.core.authentication.data.services.AuthService
import com.aliaslzr.opchallenge.core.authentication.domain.model.AuthToken
import com.aliaslzr.opchallenge.core.authentication.domain.repository.AuthRepository
import com.aliaslzr.opchallenge.core.datastore.domain.DataStorePreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authService: AuthService,
        private val dataStorePreferenceRepository: DataStorePreferenceRepository,
    ) : AuthRepository {
        override val getTokenFromDataStore: Flow<AuthToken> =
            dataStorePreferenceRepository.getToken()

        override suspend fun getValidToken(): AuthToken {
            val actualToken = getTokenFromDataStore.first()
            return if (!actualToken.token.isNullOrEmpty() && !actualToken.isExpired()) {
                actualToken
            } else {
                val newToken = getAuthToken().first()
                dataStorePreferenceRepository.setToken(newToken)
                newToken
            }
        }

        private fun getAuthToken(): Flow<AuthToken> =
            authService
                .getTokenService(
                    grantType = "client_credentials",
                    clientId = BuildConfig.CLIENT_ID,
                    clientSecret = BuildConfig.CLIENT_SECRET,
                ).map { token ->
                    val authToken = token.accessToken
                    val expiredAt = System.currentTimeMillis() + (token.expiresIn * 1000)
                    val token = AuthToken(token = authToken, expiresAt = expiredAt)
                    token
                }
    }