package com.android_dev_challenge.status.remote_repository.webservice

import androidx.lifecycle.LifecycleOwner
import autodispose2.AutoDispose
import autodispose2.FlowableSubscribeProxy
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
import com.android_dev_challenge.status.common.Result
import com.android_dev_challenge.status.remote_repository.ErrorHandler
import com.android_dev_challenge.status.remote_repository.webservice.entity.StatusApiResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class StatusApiCall(
    private val statusApi: StatusApi,
    private val errorHandler: ErrorHandler
) {
    fun callGetStatusApi(): Flowable<Result<StatusApiResponse>> {

        return statusApi.getStatus()
            .debounce(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable: Throwable ->
                Flowable.just(Result.Failure(errorHandler.handleException(ex = throwable)))
            }
            .onErrorReturn { throwable: Throwable ->
                Flowable.just(Result.Failure(errorHandler.handleException(ex = throwable)))
                getDefaultStatusApiResponse()
            }
            .flatMap {
                Flowable.just(Result.Success(it))
            }

    }

    companion object {
        fun getDefaultStatusApiResponse(): StatusApiResponse {
            val defaultHashMap: LinkedHashMap<String, StatusApiResponse.Status> =
                emptyMap<String, StatusApiResponse.Status>() as LinkedHashMap<String, StatusApiResponse.Status>
            return StatusApiResponse(defaultHashMap, defaultHashMap)
        }
    }
}

