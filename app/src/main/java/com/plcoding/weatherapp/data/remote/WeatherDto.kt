package com.plcoding.weatherapp.data.remote

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "hourly")  //parsing data from web
    val weatherData: WeatherDataDto

)
