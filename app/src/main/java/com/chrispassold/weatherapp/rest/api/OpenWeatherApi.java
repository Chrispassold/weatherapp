package com.chrispassold.weatherapp.rest.api;

import com.chrispassold.weatherapp.storage.api.WeatherCityData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    @GET("2.5/forecast")
    Call<WeatherCityData> getForecastById(@Query("id") long id, @Query("cnt") int qtd);

    @GET("2.5/forecast")
    Call<WeatherCityData> getForecastByCityName(@Query("q") String cityName, @Query("cnt") int qtd);
}