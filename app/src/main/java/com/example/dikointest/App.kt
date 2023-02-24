package com.example.dikointest

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

public class App: Application() {
    val appModule = module {
        single { SomeDB(applicationContext) }
        viewModel { params -> MyViewModel(get(),params.get(), params.get()) }
        scope<FirstFragment> {
            scoped {params -> MyFactoryForFragmentClass(params.get()) }
        }
        scope<SecondFragment> {
            scoped {params -> MyFactoryForFragmentClass(params.get()) }
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@App)
            // Load modules
            modules(appModule)
        }
    }
}