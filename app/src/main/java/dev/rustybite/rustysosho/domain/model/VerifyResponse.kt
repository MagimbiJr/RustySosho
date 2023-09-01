package dev.rustybite.rustysosho.domain.model

import dev.rustybite.rustysosho.data.dto.auth.VerifiedUserResponse

data class VerifyResponse(
    val accessToken: String,
    val expiresAt: Int,
    val refreshToken: String,
    val user: VerifiedUserResponse
)
