package com.rajawali.core.domain.result

sealed class UCResult<out R> {
    data class Success<out T>(val data: T) : UCResult<T>()
    data class Error(val errorMessage: String) : UCResult<Nothing>()
}
