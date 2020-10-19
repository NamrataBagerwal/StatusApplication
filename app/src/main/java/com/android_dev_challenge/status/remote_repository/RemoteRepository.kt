package com.android_dev_challenge.status.remote_repository

import com.android_dev_challenge.status.common.Result
import com.android_dev_challenge.status.remote_repository.webservice.StatusApiCall
import com.android_dev_challenge.status.remote_repository.webservice.entity.StatusApiResponse
import io.reactivex.rxjava3.core.Flowable

class RemoteRepository(private val statusApiCall: StatusApiCall) {

    fun getStatus(): Flowable<Result<StatusApiResponse>> = statusApiCall.callGetStatusApi()

}