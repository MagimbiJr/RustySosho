package dev.rustybite.rustysosho.data.remote

import dev.rustybite.rustysosho.data.dto.auth.AuthResponse
import dev.rustybite.rustysosho.data.dto.auth.VerifiedResponse
import dev.rustybite.rustysosho.util.RustyConstants
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RustySoshoService {
    @POST("auth/v1/otp")
    @Headers("apikey: ${RustyConstants.API_KEY}", "Content-Type: application/json")
    suspend fun authenticate(
        @Body data: JSONObject
    ): AuthResponse

    @POST("auth/v1/verify")
    @Headers("apikey: ${RustyConstants.API_KEY}", "Content-Type: application/json")
    suspend fun verifyNumber(
        @Body data: JSONObject
    ): VerifiedResponse
}