package com.plcoding.weatherapp.data.remote.repository

import com.plcoding.weatherapp.data.remote.WeatherApi
import com.plcoding.weatherapp.data.remote.toWeatherInfo
import com.plcoding.weatherapp.domain.repository.WeatherRepository
import com.plcoding.weatherapp.domain.util.Resource
import com.plcoding.weatherapp.domain.weather.WeatherInfo
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try{
            Resource.Success(   // Maping weather data to our Weather Info object
                data= api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )


        } catch(e: Exception){
            e.printStackTrace()
            Resource.Error(e.message ?:"Unknown Error Has Occured")
        }
    }

}