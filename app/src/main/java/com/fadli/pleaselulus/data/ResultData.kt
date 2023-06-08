package com.fadli.pleaselulus.data

sealed class ResultData<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null
) {
    class Loading<T>: ResultData<T>()
    class Success<T>(data: T): ResultData<T>(data = data)
    class Error<T>(message: String? = "error get data", code: Int?, data: T?): ResultData<T>(message = message, code = code, data = data)
}