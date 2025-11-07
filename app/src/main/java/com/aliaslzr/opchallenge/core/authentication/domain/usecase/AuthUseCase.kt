package com.aliaslzr.opchallenge.core.authentication.domain.usecase

import com.aliaslzr.opchallenge.core.authentication.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        suspend operator fun invoke() = authRepository.getValidToken()
    }