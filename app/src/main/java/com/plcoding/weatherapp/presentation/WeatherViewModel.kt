package com.plcoding.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.weatherapp.domain.location.LocationTracker
import com.plcoding.weatherapp.domain.repository.WeatherRepository
import com.plcoding.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel(){

    var state by mutableStateOf(WeatherState())
        private set  //only viewmodel can change this state

    fun loadWeatherInfo(){//call weather API to get location and map to state
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location->
                when(val result = repository.getWeatherData(location.latitude,location.longitude)){
                    is Resource.Success ->{
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error ->{ //weather data cant be retrived
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )

                    }
            }
            }?: kotlin.run { //cant get a location
                state = state.copy(
                    isLoading = false,
                    error = "Location Permissions Disabled"
                )
            }

        }
    }

}