package dev.rustybite.rustysosho.data.local

import com.auth0.android.jwt.JWT

class DecoderJWT {
    fun isTokenExpired(token: String, expiredAt: Long): Boolean {
        val decodedJwt = JWT(token)
        return decodedJwt.isExpired(expiredAt)
    }
}