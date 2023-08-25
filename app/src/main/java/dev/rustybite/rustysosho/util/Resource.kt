package dev.rustybite.rustysosho.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)

    class Failure(message: String) : Resource<String>(message = message)

    data object Loading : Resource<Nothing>()
}
