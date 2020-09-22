package com.example.hcassessment.core.network

import com.example.hcassessment.core.data.pojo.group.WeatherGroupResponse
import com.example.hcassessment.core.data.pojo.group.WeatherItem
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class WeatherRepository @Inject constructor(
    @Named("Backend") private val service: AppApi
) {

    fun getWeatherGroup(ids: String): Maybe<WeatherGroupResponse>{
        return service.getWeatherGroups(ids).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getWeatherDetails(city: String): Maybe<WeatherItem>{
        return service.getWeatherDetails(city).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}