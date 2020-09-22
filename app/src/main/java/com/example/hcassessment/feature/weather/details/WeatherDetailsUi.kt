package com.example.hcassessment.feature.weather.details

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.hcassessment.BR

class WeatherDetailsUi: BaseObservable() {

    @get:Bindable
    var loading: Boolean = false
        set(loading) {
            field = loading
            notifyPropertyChanged(BR.loading)
        }

    @get:Bindable
    var error: String? = null
        set(error) {
            field = error
            notifyPropertyChanged(BR.error)
        }
}