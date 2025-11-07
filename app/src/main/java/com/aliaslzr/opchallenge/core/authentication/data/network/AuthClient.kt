package com.aliaslzr.opchallenge.core.authentication.data.network

import com.aliaslzr.opchallenge.core.authentication.data.model.TokenResponse
import com.aliaslzr.opchallenge.utils.TOKEN_PATH
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthClient {
    @FormUrlEncoded
    @POST(TOKEN_PATH)
    suspend fun getTokenClient(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): Response<TokenResponse>
}