package com.chrispassold.weatherapp.rest.api;

import com.chrispassold.weatherapp.storage.pojo.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    @GET("2.5/weather")
    Call<WeatherData> getWeatherById(@Query("id") long id);

    @GET("2.5/weather")
    Call<WeatherData> getWeatherByCityName(@Query("q") String cityName);
}