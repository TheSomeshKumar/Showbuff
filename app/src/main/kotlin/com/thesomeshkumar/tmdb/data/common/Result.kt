package com.thesomeshkumar.tmdb.data.common

sealed class Result<out T> {
    data class Success<out T>(val response: T) : Result<T>()
    data class Error(val exception: RemoteSourceException) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

inline fun <T : Any> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) action(response)
    return this
}

inline fun <T : Any> Result<T>.onError(action: (RemoteSourceException) -> Unit): Result<T> {
    if (this is Result.Error) action(exception)
    return this
}

inline fun <T : Any> Result<T>.onLoading(action: () -> Unit): Result<T> {
    if (this is Result.Loading) action()
    return this
}
