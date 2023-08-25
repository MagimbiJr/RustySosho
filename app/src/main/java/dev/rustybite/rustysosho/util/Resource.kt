package dev.rustybite.rustysosho.util

sealed class Resource<out T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)

    class Failure<T>(message: String?) : Resource<T>(message = message)

    data object Loading : Resource<Nothing>()
}
