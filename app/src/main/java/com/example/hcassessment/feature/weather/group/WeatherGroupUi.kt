package com.example.hcassessment.feature.weather.group

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.hcassessment.BR

class WeatherGroupUi: BaseObservable() {

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