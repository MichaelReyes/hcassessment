package com.example.hcassessment.core.custom.weather_details

interface WeatherDetailsViewInterface {
    var temperature: Double?
    var temperatureMax: Double?
    var temperatureMin: Double?
    var name: String?
    var weather: String?
    var isFavorite: Boolean
}