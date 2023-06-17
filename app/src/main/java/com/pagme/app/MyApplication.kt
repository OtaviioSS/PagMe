package com.pagme.app

import android.app.Application
import com.pagme.app.di.component.AppComponent
import com.pagme.app.di.component.DaggerAppComponent
import com.pagme.app.di.module.AppModule


class MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .build()
    }

}

