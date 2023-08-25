package dev.rustybite.rustysosho.domain.use_cases

import com.google.gson.JsonObject
import dev.rustybite.rustysosho.data.dto.auth.toVerifyResponse
import dev.rustybite.rustysosho.data.repository.AuthRepository
import dev.rustybite.rustysosho.domain.model.VerifyResponse
import dev.rustybite.rustysosho.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class VerifyNumberUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(data: JsonObject): Flow<Resource<VerifyResponse>> = flow {
        try {
            emit(Resource.Loading)
            val response = repository.verifyNumber(data).toVerifyResponse()
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            emit(Resource.Failure(exception.localizedMessage))
        } catch (exception: IOException) {
            emit(Resource.Failure(exception.localizedMessage))
        }
    }

}