package dev.rustybite.rustysosho.data.dto.auth


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("message_id")
    val messageId: String
)