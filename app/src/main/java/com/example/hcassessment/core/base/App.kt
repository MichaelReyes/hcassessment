package com.example.hcassessment.core.base

import com.example.hcassessment.core.di.component.DaggerApplicationComponent
import com.orhanobut.hawk.Hawk
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class App: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).build()
    }

}