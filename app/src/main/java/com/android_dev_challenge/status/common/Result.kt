package com.android_dev_challenge.status.common

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val value: T) : Result<T>()
    data class Failure(val errorHolder: ErrorHolder) : Result<Nothing>()
}