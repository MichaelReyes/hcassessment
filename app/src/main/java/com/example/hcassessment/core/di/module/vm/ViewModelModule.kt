package com.example.hcassessment.core.di.module.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hcassessment.feature.weather.details.WeatherDetailsViewModel
import com.example.hcassessment.feature.weather.group.WeatherGroupViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherGroupViewModel::class)
    abstract fun bindsWeatherGroupViewModel(viewModel: WeatherGroupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherDetailsViewModel::class)
    abstract fun bindsWeatherDetailsViewModel(viewModel: WeatherDetailsViewModel): ViewModel
}