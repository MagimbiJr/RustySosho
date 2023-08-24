package dev.rustybite.rustysosho.data.dto.auth


import com.google.gson.annotations.SerializedName

data class Identity(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("identity_data")
    val identityData: IdentityData,
    @SerializedName("last_sign_in_at")
    val lastSignInAt: String,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: String
)