package com.thesomeshkumar.showbuff.data.common

import retrofit2.Response

suspend fun <T : Any> handleResponse(
    execute: suspend () -> Response<T>
): Result<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body)
        } else {
            Result.Error(RequestErrorHandler.getRequestError(Throwable()))
        }
    } catch (e: Throwable) {
        Result.Error(RequestErrorHandler.getRequestError(e))
    }
}
