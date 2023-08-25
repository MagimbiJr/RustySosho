package dev.rustybite.rustysosho.data.repository

import com.google.gson.JsonObject
import dev.rustybite.rustysosho.data.dto.auth.AuthResponseDto
import dev.rustybite.rustysosho.data.dto.auth.VerifiedResponseDto

interface AuthRepository {
    suspend fun authenticate(data: JsonObject): AuthResponseDto

    suspend fun verifyNumber(data: JsonObject): VerifiedResponseDto
}