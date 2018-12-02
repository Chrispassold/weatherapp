package com.chrispassold.weatherapp;

import android.app.Application;


public class WeatherAppApplication extends Application {

    private static WeatherAppApplication _instance;

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = this;

    }

    public static WeatherAppApplication getContext() {
        return _instance;
    }
}
