package com.chrispassold.weatherapp.rest.config;

import com.chrispassold.weatherapp.rest.api.OpenWeatherApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherApiClient {
    private static Retrofit retrofit = null;
    //todo: trocar
    static String OPEN_WEATHER_API_KEY = "000910f83eed986c6ea07def7e34311a";

    public static OpenWeatherApi getClient() {

        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(new OpenWeatherInterceptor())
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://samples.openweathermap.org/data/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit.create(OpenWeatherApi.class);
    }
}