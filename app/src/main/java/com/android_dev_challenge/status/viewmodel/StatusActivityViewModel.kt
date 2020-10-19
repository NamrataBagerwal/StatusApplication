package com.android_dev_challenge.status.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android_dev_challenge.status.common.Result
import com.android_dev_challenge.status.ui.dto.StatusDto
import com.android_dev_challenge.status.usecase.StatusUseCase

class StatusActivityViewModel(private val statusUseCase: StatusUseCase) : ViewModel() {

    private val _index = MutableLiveData<Int>()

    private lateinit var statusLiveData: LiveData<Result<StatusDto>>

    private fun updateStatusLiveData() {
        statusLiveData =
            LiveDataReactiveStreams.fromPublisher(statusUseCase.getStatus(_index.value))
    }

    fun getStatusLiveData(): LiveData<Result<StatusDto>> {
        return statusLiveData
    }

    fun setTabIndex(index: Int) {
        _index.value = index
        updateStatusLiveData()
    }
}


