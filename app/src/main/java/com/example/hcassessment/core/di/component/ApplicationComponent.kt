package com.example.hcassessment.core.di.component

import com.example.hcassessment.core.base.App
import com.example.hcassessment.core.di.module.ActivityBuilderModule
import com.example.hcassessment.core.di.module.AppModule
import com.example.hcassessment.core.di.module.FragmentBuilderModule
import com.example.hcassessment.core.di.module.NetworkModule
import com.example.hcassessment.core.di.module.vm.ViewModelModule
import com.example.hcassessment.core.di.scope.AppScope
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class,
        NetworkModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<App> {
    @Component.Factory
    interface Factory: AndroidInjector.Factory<App>
}