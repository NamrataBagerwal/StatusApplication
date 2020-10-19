package com.android_dev_challenge.status.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android_dev_challenge.status.common.AppConstants
import com.android_dev_challenge.status.common.Result
import com.android_dev_challenge.status.common.StatusApplicationTest
import com.android_dev_challenge.status.di.DependencyInjectionModule
import com.android_dev_challenge.status.remote_repository.webservice.entity.StatusApiResponse
import com.android_dev_challenge.status.ui.dto.StatusDto
import com.android_dev_challenge.status.usecase.StatusUseCase
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Flowable
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.robolectric.annotation.Config

@RunWith(MockitoJUnitRunner::class)
class StatusActivityViewModelTest : KoinTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val viewModel: StatusActivityViewModel by inject()

    private lateinit var statusUseCase: StatusUseCase

    companion object {
        const val INDEX = 0
    }

    @Before
    fun setUp() {

        startKoin {
            modules(
                listOf(
                    DependencyInjectionModule.common,
                    DependencyInjectionModule.statusApiModule,
                    DependencyInjectionModule.viewModelModule
                )
            )
        }

        statusUseCase = declareMock()
    }

    @Test
    fun getStatusLiveData() {
        viewModel.setTabIndex(INDEX)
        whenever(statusUseCase.getStatus(INDEX)).thenReturn(getStatusDtoForFirstTab())
        verify(statusUseCase).getStatus(INDEX)
        assertNotNull(viewModel.getStatusLiveData())
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun getStatusDtoForFirstTab(): Flowable<Result<StatusDto>> {
        val apisDbsHashmap = mutableMapOf<String, StatusApiResponse.Status>()
        apisDbsHashmap["Key"] =
            StatusApiResponse.Status("url1", 200, Double.MIN_VALUE, "statusClass1")
        val sitesHashmap = mutableMapOf<String, StatusApiResponse.Status>()
        apisDbsHashmap["Key"] =
            StatusApiResponse.Status("url2", 200, Double.MIN_VALUE, "statusClass2")
        val statusApiResponse = StatusApiResponse(
            apisDbsHashmap as LinkedHashMap<String, StatusApiResponse.Status>,
            sitesHashmap as LinkedHashMap<String, StatusApiResponse.Status>
        )

        val statusDto = StatusDto(AppConstants.APIS_DBS, statusApiResponse.apisDBs.flatMap {
            listOf(
                StatusDto.ChildStatus(
                    AppConstants.APIS_DBS,
                    it.key,
                    it.value.url,
                    it.value.responseCode,
                    it.value.responseTime,
                    it.value.statusClass
                )
            )
        })
        return Flowable.just(Result.Success(statusDto))
    }

}