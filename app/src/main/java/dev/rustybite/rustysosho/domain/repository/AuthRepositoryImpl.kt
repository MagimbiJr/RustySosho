package dev.rustybite.rustysosho.domain.repository

import dev.rustybite.rustysosho.data.dto.auth.AuthResponse
import dev.rustybite.rustysosho.data.dto.auth.VerifiedResponse
import dev.rustybite.rustysosho.data.remote.RustySoshoService
import dev.rustybite.rustysosho.data.repository.AuthRepository
import org.json.JSONObject
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: RustySoshoService
) : AuthRepository {
    override suspend fun authenticate(data: JSONObject): AuthResponse {
        return service.authenticate(data)
    }

    override suspend fun verifyNumber(data: JSONObject): VerifiedResponse {
        return service.verifyNumber(data)
    }
}