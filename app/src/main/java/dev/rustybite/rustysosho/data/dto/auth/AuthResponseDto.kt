package dev.rustybite.rustysosho.data.dto.auth


import com.google.gson.annotations.SerializedName
import dev.rustybite.rustysosho.domain.model.AuthResponse

data class AuthResponseDto(
    @SerializedName("message_id")
    val messageId: String
)

fun AuthResponseDto.toAuthResponse(): AuthResponse =
    AuthResponse(
        messageId = messageId
    )