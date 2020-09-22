package com.example.hcassessment.core.custom.ui

import androidx.databinding.BindingAdapter

object WeatherGroupViewBindingAdapter {

    @BindingAdapter("view_temperature")
    @JvmStatic fun viewTemperature(view: WeatherGroupView, value: String?){
        view.temperature = value
    }

    @BindingAdapter("view_name")
    @JvmStatic fun viewName(view: WeatherGroupView, value: String?){
        view.name = value
    }

    @BindingAdapter("view_weather")
    @JvmStatic fun viewWeather(view: WeatherGroupView, value: String?){
        view.weather = value
    }

    @BindingAdapter("view_isFavorite")
    @JvmStatic fun viewIsFavorite(view: WeatherGroupView, value: Boolean?){
        view.isFavorite = value?:false
    }


}