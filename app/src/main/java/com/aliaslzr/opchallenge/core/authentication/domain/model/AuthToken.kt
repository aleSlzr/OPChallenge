package com.aliaslzr.opchallenge.core.authentication.domain.model

data class AuthToken(
    val token: String,
    val expiresAt: Long,
) {
    fun isExpired(): Boolean = System.currentTimeMillis() > expiresAt
}