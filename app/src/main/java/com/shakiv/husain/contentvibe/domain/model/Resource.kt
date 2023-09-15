package com.shakiv.husain.contentvibe.domain.model

sealed class Resource<T>(
    val data: T? = null, //T : Anything
    val message: String? = null,
    val code: Int? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T? = null, message: String? = null, code: Int? = null) :
        Resource<T>(data, message, code)

    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(
        message: String = "Something went wrong",
        data: T? = null,
        error: Throwable? = null,
        code: Int? = null
    ) : Resource<T>(data, message, error = error)
}