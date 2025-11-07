package com.aliaslzr.opchallenge.core.di.modules

import com.aliaslzr.opchallenge.core.authentication.data.repository.AuthRepositoryImpl
import com.aliaslzr.opchallenge.core.authentication.data.services.AuthService
import com.aliaslzr.opchallenge.core.authentication.data.services.AuthServiceImpl
import com.aliaslzr.opchallenge.core.authentication.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    abstract fun bindAuthService(
        authServiceImpl: AuthServiceImpl,
    ): AuthService
}