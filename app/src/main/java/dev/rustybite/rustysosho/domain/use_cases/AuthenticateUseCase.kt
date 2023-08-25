package dev.rustybite.rustysosho.domain.use_cases

import com.google.gson.JsonObject
import dev.rustybite.rustysosho.data.dto.auth.toAuthResponse
import dev.rustybite.rustysosho.data.repository.AuthRepository
import dev.rustybite.rustysosho.domain.model.AuthResponse
import dev.rustybite.rustysosho.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AuthenticateUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(data: JsonObject): Flow<Resource<AuthResponse>> = flow {
        try {
            emit(Resource.Loading)
            val response = repository.authenticate(data).toAuthResponse()
            emit(Resource.Success(data = response))
        } catch (exception: Exception) {
            emit(Resource.Failure(message = exception.localizedMessage))
        } catch (exception: IOException) {
            emit(Resource.Failure(message = exception.localizedMessage))
        }
    }

}