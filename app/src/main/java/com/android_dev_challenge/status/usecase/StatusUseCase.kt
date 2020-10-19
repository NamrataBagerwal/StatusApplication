package com.android_dev_challenge.status.usecase

import com.android_dev_challenge.status.common.AppConstants.APIS_DBS
import com.android_dev_challenge.status.common.AppConstants.SITES
import com.android_dev_challenge.status.common.Result
import com.android_dev_challenge.status.remote_repository.RemoteRepository
import com.android_dev_challenge.status.remote_repository.webservice.entity.StatusApiResponse
import com.android_dev_challenge.status.ui.dto.StatusDto
import io.reactivex.rxjava3.core.Flowable

class StatusUseCase(private val remoteRepository: RemoteRepository) {

    fun getStatus(position: Int?): Flowable<Result<StatusDto>> {
        return remoteRepository.getStatus()
            .flatMap { result: Result<StatusApiResponse> ->
                when (result) {
                    is Result.Failure -> {
                        println("Result.Failure")
                        Flowable.just(Result.Failure(result.errorHolder))
                    }
                    is Result.Success -> {
                        println("Result.Success")
                        val statusApiResponse = result.value
                        val statusDto: StatusDto = when (position) {
                            0 -> getStatusDtoForFirstTab(statusApiResponse)
                            1 -> {
                                StatusDto(SITES, statusApiResponse.sites.flatMap {
                                    listOf(
                                        StatusDto.ChildStatus(
                                            SITES,
                                            it.key,
                                            it.value.url,
                                            it.value.responseCode,
                                            it.value.responseTime,
                                            it.value.statusClass
                                        )
                                    )
                                })
                            }
                            else -> getStatusDtoForFirstTab(statusApiResponse)
                        }

                        Flowable.just(Result.Success(statusDto))
                    }
                }
            }
    }

    private fun getStatusDtoForFirstTab(statusApiResponse: StatusApiResponse): StatusDto {
        return StatusDto(APIS_DBS, statusApiResponse.apisDBs.flatMap {
            listOf(
                StatusDto.ChildStatus(
                    APIS_DBS,
                    it.key,
                    it.value.url,
                    it.value.responseCode,
                    it.value.responseTime,
                    it.value.statusClass
                )
            )
        })
    }
}
