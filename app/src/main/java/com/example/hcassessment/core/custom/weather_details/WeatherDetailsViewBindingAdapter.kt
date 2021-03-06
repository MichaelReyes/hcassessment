package com.example.hcassessment.core.custom.weather_details

import androidx.databinding.BindingAdapter
import com.example.hcassessment.core.data.pojo.group.WeatherItem

object WeatherDetailsViewBindingAdapter {

    @BindingAdapter("view_weather_details")
    @JvmStatic fun viewWeatherDetails(view: WeatherDetailsView, value: WeatherItem?){
        value?.apply {
            view.temperature = main.temp
            view.temperatureMax = main.temp_max
            view.temperatureMin = main.temp_min
            view.name = name
            view.weather = weather.firstOrNull()?.main?:"No Value"
            view.isFavorite = isFavorite
        }
    }

    @BindingAdapter("details_view_temperature")
    @JvmStatic fun viewTemperature(view: WeatherDetailsView, value: Double?){
        view.temperature = value
    }

    @BindingAdapter("details_view_temperatureMin")
    @JvmStatic fun viewTemperatureMin(view: WeatherDetailsView, value: Double?){
        view.temperatureMin = value
    }

    @BindingAdapter("details_view_temperatureMax")
    @JvmStatic fun viewTemperatureMax(view: WeatherDetailsView, value: Double?){
        view.temperatureMax = value
    }

    @BindingAdapter("details_view_name")
    @JvmStatic fun viewName(view: WeatherDetailsView, value: String?){
        view.name = value
    }

    @BindingAdapter("details_view_weather")
    @JvmStatic fun viewWeather(view: WeatherDetailsView, value: String?){
        view.weather = value
    }

    @BindingAdapter("details_view_isFavorite")
    @JvmStatic fun viewIsFavorite(view: WeatherDetailsView, value: Boolean?){
        view.isFavorite = value?:false
    }


}