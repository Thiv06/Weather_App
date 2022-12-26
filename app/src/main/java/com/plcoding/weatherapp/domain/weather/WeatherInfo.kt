package com.plcoding.weatherapp.domain.weather

data class WeatherInfo( //weather data per day(temp, humidity, pressure, etc
    val weatherDataPerDay: Map<Int, List<WeatherData>>, //list of weather data of a certain day
    val currentWeatherData: WeatherData?  //data present
)
