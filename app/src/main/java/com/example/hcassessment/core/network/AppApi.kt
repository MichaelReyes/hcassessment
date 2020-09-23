package com.example.hcassessment.core.network

import com.example.hcassessment.BuildConfig
import com.example.hcassessment.core.data.pojo.group.WeatherGroupResponse
import com.example.hcassessment.core.data.pojo.group.WeatherItem
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Query


interface AppApi {

    @GET("data/2.5/group?units=metric&appid=${BuildConfig.OPEN_WEATHER_API_KEY}")
    fun getWeatherGroups(@Query("id") ids: String): Maybe<WeatherGroupResponse>

    @GET("data/2.5/weather?units=metric&appid=${BuildConfig.OPEN_WEATHER_API_KEY}")
    fun getWeatherDetails(@Query("q") city: String): Maybe<WeatherItem>
}