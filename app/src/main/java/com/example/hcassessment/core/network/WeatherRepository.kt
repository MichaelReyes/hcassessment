package com.example.hcassessment.core.network

import com.example.hcassessment.core.data.pojo.group.WeatherGroupResponse
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

}