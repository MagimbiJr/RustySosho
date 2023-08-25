package dev.rustybite.rustysosho.data.repository

import dev.rustybite.rustysosho.data.dto.auth.AuthResponse
import dev.rustybite.rustysosho.data.dto.auth.VerifiedResponse
import org.json.JSONObject

interface AuthRepository {
    suspend fun authenticate(data: JSONObject): AuthResponse

    suspend fun verifyNumber(data: JSONObject): VerifiedResponse
}