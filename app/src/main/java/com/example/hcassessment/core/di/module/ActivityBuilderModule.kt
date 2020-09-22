package com.example.hcassessment.core.di.module

import com.example.hcassessment.core.di.scope.ActivityScope
import com.example.hcassessment.feature.weather.DashboardActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindDashboardActivity(): DashboardActivity

}