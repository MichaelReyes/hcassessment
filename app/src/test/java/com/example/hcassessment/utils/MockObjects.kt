package com.example.hcassessment.utils

import com.example.hcassessment.core.data.pojo.group.WeatherGroupResponse
import com.example.hcassessment.core.data.pojo.group.WeatherItem
import com.example.hcassessment.core.extensions.fromJson
import com.google.gson.Gson
import io.reactivex.Maybe
import io.reactivex.internal.operators.maybe.MaybeJust
import java.lang.Exception


val manilaWeatherIdOnly: WeatherItem = Gson().fromJson("{\"id\":1701668,\"name\":\"Manila\"}")
val manilaWeather: WeatherItem = Gson().fromJson("{\"coord\":{\"lon\":120.98,\"lat\":14.6},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"base\":\"stations\",\"main\":{\"temp\":301.54,\"feels_like\":306.63,\"temp_min\":300.93,\"temp_max\":302.15,\"pressure\":1009,\"humidity\":83},\"visibility\":9000,\"wind\":{\"speed\":2.1,\"deg\":190},\"rain\":{\"1h\":1.4},\"clouds\":{\"all\":90},\"dt\":1600858654,\"sys\":{\"type\":1,\"id\":8160,\"country\":\"PH\",\"sunrise\":1600811110,\"sunset\":1600854707},\"timezone\":28800,\"id\":1701668,\"name\":\"Manila\",\"cod\":200}")
val praugeWeather: WeatherItem = Gson().fromJson("{\"coord\":{\"lon\":14.42,\"lat\":50.09},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":295.08,\"feels_like\":294.11,\"temp_min\":294.15,\"temp_max\":295.93,\"pressure\":1007,\"humidity\":60},\"visibility\":10000,\"wind\":{\"speed\":3.1,\"deg\":140},\"clouds\":{\"all\":93},\"dt\":1600859043,\"sys\":{\"type\":1,\"id\":6835,\"country\":\"CZ\",\"sunrise\":1600836633,\"sunset\":1600880319},\"timezone\":7200,\"id\":3067696,\"name\":\"Prague\",\"cod\":200}")
val seoulWeather: WeatherItem = Gson().fromJson("{\"coord\":{\"lon\":126.98,\"lat\":37.57},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"base\":\"stations\",\"main\":{\"temp\":293.28,\"feels_like\":291.49,\"temp_min\":292.15,\"temp_max\":294.15,\"pressure\":1017,\"humidity\":52},\"visibility\":10000,\"wind\":{\"speed\":2.6,\"deg\":40},\"clouds\":{\"all\":90},\"dt\":1600859000,\"sys\":{\"type\":1,\"id\":8117,\"country\":\"KR\",\"sunrise\":1600809638,\"sunset\":1600853299},\"timezone\":32400,\"id\":1835848,\"name\":\"Seoul\",\"cod\":200}")

val weatherGroup: WeatherGroupResponse = WeatherGroupResponse(cnt = 3, list = listOf(manilaWeather, praugeWeather, seoulWeather))

fun getCityIds(): List<String> = listOf("1701668","3067696","1835848")
fun getCityIdsNotFound(): List<String> = listOf("2222","3333","4444")

fun getWeatherGroupsResponse(): Maybe<WeatherGroupResponse> = MaybeJust(weatherGroup)
fun getWeatherGroupsNotFoundResponse(): Maybe<WeatherGroupResponse> = Maybe.error(Exception("No Data"))
fun getManilaWeather() : Maybe<WeatherItem> = MaybeJust(manilaWeather)
fun getPragueWeather() : Maybe<WeatherItem> = MaybeJust(praugeWeather)
fun getSeoulWeather() : Maybe<WeatherItem> = MaybeJust(seoulWeather)

