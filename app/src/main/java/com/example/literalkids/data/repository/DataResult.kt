package com.example.literalkids.data.repository

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Failure(val errorMessage: String) : DataResult<Nothing>()
    object NetworkError : DataResult<Nothing>()
}
