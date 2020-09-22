package com.example.hcassessment.core.di.module

import com.example.hcassessment.core.di.scope.FragmentScope
import com.example.hcassessment.feature.weather.group.WeatherGroupFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindWeatherGroupFragment(): WeatherGroupFragment

}