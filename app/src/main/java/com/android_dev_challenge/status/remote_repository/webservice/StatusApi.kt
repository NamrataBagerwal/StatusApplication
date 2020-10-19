package com.android_dev_challenge.status.remote_repository.webservice

import com.android_dev_challenge.status.remote_repository.webservice.entity.StatusApiResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface StatusApi {

    @GET("status")
    fun getStatus(): Flowable<StatusApiResponse>
}