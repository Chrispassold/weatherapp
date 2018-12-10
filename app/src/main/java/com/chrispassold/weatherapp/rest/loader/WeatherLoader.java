package com.chrispassold.weatherapp.rest.loader;

import android.support.annotation.NonNull;

import com.chrispassold.weatherapp.rest.api.CallbackApi;
import com.chrispassold.weatherapp.rest.config.OpenWeatherApiClient;
import com.chrispassold.weatherapp.storage.api.WeatherCityData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class WeatherLoader {

    public static void getByCity(@NonNull String city, CallbackApi<WeatherCityData> callbackApi) {
        Call<WeatherCityData> weather = OpenWeatherApiClient.getClient().getForecastByCityName(city, 5);

        weather.enqueue(new Callback<WeatherCityData>() {
            @Override
            public void onResponse(Call<WeatherCityData> call, Response<WeatherCityData> response) {
                if (response.isSuccessful()) {
                    callbackApi.onSuccess(response.body());
                } else {
                    callbackApi.onFail(new HttpException(response), response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherCityData> call, Throwable t) {
                callbackApi.onFail(t, 500);
            }
        });
    }

    public static void getById(long city, CallbackApi<WeatherCityData> callbackApi) {
        Call<WeatherCityData> weather = OpenWeatherApiClient.getClient().getForecastById(city, 5);

        weather.enqueue(new Callback<WeatherCityData>() {
            @Override
            public void onResponse(Call<WeatherCityData> call, Response<WeatherCityData> response) {
                if (response.isSuccessful()) {
                    callbackApi.onSuccess(response.body());
                } else {
                    callbackApi.onFail(new HttpException(response), response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherCityData> call, Throwable t) {
                callbackApi.onFail(t, 500);
            }
        });
    }

}
