package com.chrispassold.weatherapp.rest.config;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;

public class OpenWeatherInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        HttpUrl url = chain.request().url().newBuilder()
                .addQueryParameter("appid", OpenWeatherApiClient.OPEN_WEATHER_API_KEY)
                .addQueryParameter("units", "metric")
                .build();

        return chain.proceed(chain
                .request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .url(url)
                .build()
        );
    }
}
