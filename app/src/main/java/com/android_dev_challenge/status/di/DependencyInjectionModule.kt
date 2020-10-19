package com.android_dev_challenge.status.di

import com.android_dev_challenge.status.BuildConfig
import com.android_dev_challenge.status.remote_repository.ErrorHandler
import com.android_dev_challenge.status.remote_repository.RemoteRepository
import com.android_dev_challenge.status.remote_repository.networking_retrofit.RetrofitFactory
import com.android_dev_challenge.status.remote_repository.webservice.StatusApi
import com.android_dev_challenge.status.remote_repository.webservice.StatusApiCall
import com.android_dev_challenge.status.usecase.StatusUseCase
import com.android_dev_challenge.status.viewmodel.StatusActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyInjectionModule {
    val common = module {
        single { ErrorHandler() }
    }

    val statusApiModule = module {
        single {
            val api: StatusApi =
                RetrofitFactory.retrofit(BuildConfig.BASE_URL).create(StatusApi::class.java)
            return@single StatusApiCall(api, get())
        }
        single { RemoteRepository(get()) }
        single { StatusUseCase(get()) }
    }

    // Initializing ViewModel Modules
    val viewModelModule = module {
        viewModel {
            StatusActivityViewModel(get())
        }
    }

}