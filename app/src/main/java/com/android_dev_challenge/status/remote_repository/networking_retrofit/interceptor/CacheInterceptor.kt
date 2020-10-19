package com.android_dev_challenge.status.remote_repository.networking_retrofit.interceptor

import com.android_dev_challenge.status.common.Result
import com.android_dev_challenge.status.remote_repository.ErrorHandler
import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor(private val errorHandler: ErrorHandler) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)
        val cacheControl = originalResponse.header("Cache-Control")

        return if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
            cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
        ) {
            val response = originalResponse.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=" + 5000)
                .build()

//            checkResponse(response)
            println("CacheInterceptor - if")
            response

        } else {
            println("CacheInterceptor - else")
//            checkResponse(originalResponse)
            originalResponse
        }
    }

    private fun checkResponse(response: Response) {
        if (!response.isSuccessful) {
            Result.Failure(errorHandler.handleException(response.code))
        }
    }
}
