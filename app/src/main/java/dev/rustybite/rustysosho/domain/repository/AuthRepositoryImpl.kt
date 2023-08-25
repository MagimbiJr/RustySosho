package dev.rustybite.rustysosho.domain.repository

import com.google.gson.JsonObject
import dev.rustybite.rustysosho.data.dto.auth.AuthResponseDto
import dev.rustybite.rustysosho.data.dto.auth.VerifiedResponseDto
import dev.rustybite.rustysosho.data.remote.RustySoshoService
import dev.rustybite.rustysosho.data.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: RustySoshoService
) : AuthRepository {
    override suspend fun authenticate(data: JsonObject): AuthResponseDto {
        return service.authenticate(data)
    }

    override suspend fun verifyNumber(data: JsonObject): VerifiedResponseDto {
        return service.verifyNumber(data)
    }
}