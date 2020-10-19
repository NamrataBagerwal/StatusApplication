package com.android_dev_challenge.status.common

import android.app.Application
import com.android_dev_challenge.status.di.DependencyInjectionModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StatusApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // get list of all modules
        val diModuleList = listOf(
            DependencyInjectionModule.common,
            DependencyInjectionModule.statusApiModule,
            DependencyInjectionModule.viewModelModule
        )
        // start koin with the module list
        startKoin {
            // Android context
            androidContext(this@StatusApplication)
            // modules
            modules(diModuleList)
        }
    }
}