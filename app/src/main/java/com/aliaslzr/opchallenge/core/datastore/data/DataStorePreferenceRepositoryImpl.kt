package com.aliaslzr.opchallenge.core.datastore.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.aliaslzr.opchallenge.core.authentication.domain.model.AuthToken
import com.aliaslzr.opchallenge.core.datastore.domain.DataStorePreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreferenceRepositoryImpl
    @Inject
    constructor(
        private val dataStorePreferences: DataStore<Preferences>,
    ) : DataStorePreferenceRepository {
        override fun getToken(): Flow<AuthToken> =
            dataStorePreferences
                .data
                .map { preferences ->
                    val token = preferences[PreferencesKeys.TOKEN] ?: ""
                    val expiresAt = preferences[PreferencesKeys.EXPIRES_AT] ?: 0
                    AuthToken(
                        token = token,
                        expiresAt = expiresAt,
                    )
                }

        override suspend fun setToken(token: AuthToken) {
            dataStorePreferences.edit { preferences ->
                preferences[PreferencesKeys.TOKEN] = token.token
                preferences[PreferencesKeys.EXPIRES_AT] = token.expiresAt
            }
        }

        private object PreferencesKeys {
            val TOKEN = stringPreferencesKey(name = "bearer_token")
            val EXPIRES_AT = longPreferencesKey(name = "expires_at")
        }
    }