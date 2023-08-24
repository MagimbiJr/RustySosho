package dev.rustybite.rustysosho.data.dto.auth


import com.google.gson.annotations.SerializedName

data class VerifiedUserResponse(
    @SerializedName("app_metadata")
    val appMetadata: AppMetadata,
    @SerializedName("aud")
    val aud: String,
    @SerializedName("confirmation_sent_at")
    val confirmationSentAt: String,
    @SerializedName("confirmed_at")
    val confirmedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("identities")
    val identities: List<Identity>,
    @SerializedName("last_sign_in_at")
    val lastSignInAt: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("phone_confirmed_at")
    val phoneConfirmedAt: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_metadata")
    val userMetadata: UserMetadata
)