package com.chrispassold.weatherapp.storage.control;

import com.chrispassold.weatherapp.storage.api.WeatherCityData;
import com.chrispassold.weatherapp.storage.model.WeatherCityDaysModel;
import com.chrispassold.weatherapp.storage.model.WeatherCityModel;
import com.chrispassold.weatherapp.storage.pojo.Main;
import com.chrispassold.weatherapp.storage.pojo.Weather;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Date;

public class WeatherControl {

    public void create(WeatherCityData data) throws SQLException {

        Dao<WeatherCityModel, Long> cityDao = WeatherCityModel.getDao();
        Dao<WeatherCityDaysModel, Long> cityDaysDao = WeatherCityDaysModel.getDao();

        WeatherCityModel weatherCityModel = new WeatherCityModel(data.getCity().getName(), new Date());

        cityDao.create(weatherCityModel);

        for (com.chrispassold.weatherapp.storage.pojo.List weatherData : data.getList()) {
            WeatherCityDaysModel weatherCityDaysModel = new WeatherCityDaysModel(weatherCityModel);

            Weather weather = weatherData.getWeather().get(0);
            weatherCityDaysModel.setDescription(weather.getDescription());
            weatherCityDaysModel.setIcon(weather.getIcon());
            weatherCityDaysModel.setMain(weather.getMain());

            weatherCityDaysModel.setDate(new java.sql.Date(weatherData.getDt()));
            Main main = weatherData.getMain();
            weatherCityDaysModel.setTemp(main.getTemp());
            weatherCityDaysModel.setTempMax(main.getTempMax());
            weatherCityDaysModel.setTempMin(main.getTempMin());

            cityDaysDao.create(weatherCityDaysModel);
        }

    }

}
