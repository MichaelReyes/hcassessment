package com.example.hcassessment.core.base

import android.net.NetworkInfo
import com.example.hcassessment.core.di.component.DaggerApplicationComponent
import com.example.hcassessment.core.utils.Constants
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.jakewharton.rxrelay2.BehaviorRelay
import com.orhanobut.hawk.Hawk
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers


class App: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).build()
        initNetworkObserver()
    }

    private val disposables = CompositeDisposable()

    private fun initNetworkObserver(){

        ReactiveNetwork
            .observeNetworkConnectivity(applicationContext)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                internetConnectionStream.accept(it.available() && it.state() == NetworkInfo.State.CONNECTED)
                Hawk.put(Constants.PREF_KEY_NETWORK_CONNECTED, it.available() && it.state() == NetworkInfo.State.CONNECTED)
            }.addTo(disposables)
    }

    val internetConnectionStream = BehaviorRelay.create<Boolean>()

}