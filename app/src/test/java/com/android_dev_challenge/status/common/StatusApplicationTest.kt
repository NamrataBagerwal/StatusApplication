package com.android_dev_challenge.status.common

import android.app.Application
import com.android_dev_challenge.status.di.DependencyInjectionModule
import org.junit.Test

import org.junit.Assert.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication

class StatusApplicationTest: Application() {

    override fun onCreate() {
        super.onCreate()
        // Do not invoke StatusApplication
        val diModuleList = listOf(
            DependencyInjectionModule.common,
            DependencyInjectionModule.statusApiModule,
            DependencyInjectionModule.viewModelModule
        )
        // start koin with the module list
        startKoin {
            // Android context
            androidContext(this@StatusApplicationTest)
            // modules
            modules(diModuleList)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        // Do not invoke StatusApplication
        stopKoin()
    }
}