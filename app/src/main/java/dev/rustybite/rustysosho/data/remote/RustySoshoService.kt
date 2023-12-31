package dev.rustybite.rustysosho.data.remote

import com.google.gson.JsonObject
import dev.rustybite.rustysosho.data.dto.auth.AuthResponseDto
import dev.rustybite.rustysosho.data.dto.auth.VerifiedResponseDto
import dev.rustybite.rustysosho.util.RustyConstants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RustySoshoService {
    @POST("auth/v1/otp")
    @Headers("apikey: ${RustyConstants.API_KEY}", "Content-Type: application/json")
    suspend fun authenticate(
        @Body data: JsonObject
    ): AuthResponseDto

    @POST("auth/v1/verify")
    @Headers("apikey: ${RustyConstants.API_KEY}", "Content-Type: application/json")
    suspend fun verifyNumber(
        @Body data: JsonObject
    ): VerifiedResponseDto

    @GET("rest/v1/users")
    @Headers("apiKey: ${RustyConstants.API_KEY}, Authorization: Bearer ${RustyConstants.API_KEY}")
    suspend fun getUsers()

    @POST("auth/v1/token?grant_type=refresh_token")
    @Headers("apikey: ${RustyConstants.API_KEY}")
    suspend fun refreshToken(
        @Body data: JsonObject
    ): VerifiedResponseDto
}