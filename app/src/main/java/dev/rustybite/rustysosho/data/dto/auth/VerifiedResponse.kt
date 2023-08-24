package dev.rustybite.rustysosho.data.dto.auth


import com.google.gson.annotations.SerializedName

data class VerifiedResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_at")
    val expiresAt: Int,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("user")
    val user: VerifiedUserResponse
)