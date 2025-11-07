package com.aliaslzr.opchallenge.core.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.aliaslzr.opchallenge.BuildConfig
import com.aliaslzr.opchallenge.core.datastore.data.DataStorePreferenceRepositoryImpl
import com.aliaslzr.opchallenge.core.datastore.domain.DataStorePreferenceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.userDataStore by preferencesDataStore(
    name = "${BuildConfig.APPLICATION_ID}.user_preferences",
)

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStorePreferencesModule {
    @Binds
    @Singleton
    abstract fun bindDataStorePreferencesRepository(
        dataStorePreferenceRepositoryImpl: DataStorePreferenceRepositoryImpl,
    ): DataStorePreferenceRepository

    companion object {
        @Provides
        @Singleton
        fun provideDataStorePreferences(
            @ApplicationContext applicationContext: Context,
        ): DataStore<Preferences> = applicationContext.userDataStore
    }
}