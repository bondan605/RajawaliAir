package com.rajawali.core.domain.result

sealed class CommonResult<out R> {
    data class Success<out T>(val data: T) : CommonResult<T>()
    data class Error(val errorMessage: String) : CommonResult<Nothing>()
}
