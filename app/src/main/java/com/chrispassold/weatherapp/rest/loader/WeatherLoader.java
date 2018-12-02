package com.chrispassold.weatherapp.rest.loader;

import android.support.annotation.NonNull;

import com.chrispassold.weatherapp.rest.api.CallbackApi;
import com.chrispassold.weatherapp.rest.config.OpenWeatherApiClient;
import com.chrispassold.weatherapp.storage.pojo.WeatherData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class WeatherLoader {

    public static void getByCity(@NonNull String city, CallbackApi<WeatherData> callbackApi) {
        Call<WeatherData> weatherByCityName = OpenWeatherApiClient.getClient().getWeatherByCityName(city);

        weatherByCityName.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful()) {
                    callbackApi.onSuccess(response.body());
                } else {
                    callbackApi.onFail(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                callbackApi.onFail(t);
            }
        });
    }

}
