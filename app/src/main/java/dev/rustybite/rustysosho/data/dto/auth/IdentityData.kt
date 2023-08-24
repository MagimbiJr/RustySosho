package dev.rustybite.rustysosho.data.dto.auth


import com.google.gson.annotations.SerializedName

data class IdentityData(
    @SerializedName("sub")
    val sub: String
)