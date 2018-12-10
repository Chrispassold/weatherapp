package com.chrispassold.weatherapp.storage.repository;

import com.chrispassold.weatherapp.rest.api.Callback;
import com.chrispassold.weatherapp.rest.api.CallbackApi;
import com.chrispassold.weatherapp.rest.loader.WeatherLoader;
import com.chrispassold.weatherapp.storage.api.WeatherCityData;
import com.chrispassold.weatherapp.storage.model.WeatherCityDaysModel;
import com.chrispassold.weatherapp.storage.model.WeatherCityModel;
import com.chrispassold.weatherapp.storage.pojo.Main;
import com.chrispassold.weatherapp.storage.pojo.Weather;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class WeatherRepository {

    public void create(WeatherCityData data) throws SQLException {

        Dao<WeatherCityModel, Long> cityDao = WeatherCityModel.getDao();
        WeatherCityModel weatherCityModel = new WeatherCityModel(data.getCity().getName(), new Date());

        cityDao.createOrUpdate(weatherCityModel);
        updateCityDays(data, weatherCityModel);
    }

    private void updateCityDays(WeatherCityData data, WeatherCityModel city) throws SQLException {

        Dao<WeatherCityDaysModel, Long> cityDaysDao = WeatherCityDaysModel.getDao();

        DeleteBuilder<WeatherCityDaysModel, Long> delete = cityDaysDao.deleteBuilder();
        delete.where().eq(WeatherCityDaysModel.F_CITY, city.getId());
        delete.delete();

        for (com.chrispassold.weatherapp.storage.pojo.List weatherData : data.getList()) {
            WeatherCityDaysModel weatherCityDaysModel = new WeatherCityDaysModel(city);

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

    public void refreshCities(Callback callback) throws SQLException {
        Dao<WeatherCityModel, Long> cityDao = WeatherCityModel.getDao();

        List<WeatherCityModel> weatherCityModels = cityDao.queryForAll();

        for (WeatherCityModel weatherCityModel : weatherCityModels) {
            CallbackApi<WeatherCityData> callbackApi = new CallbackApi<WeatherCityData>() {
                @Override
                public void onSuccess(WeatherCityData data) {
                    try {
                        updateCityDays(data, weatherCityModel);
                        weatherCityModel.setLastUpdate(new Date());
                        cityDao.update(weatherCityModel);

                        callback.onSuccess();
                    } catch (SQLException e) {
                        onFail(e, 500);
                    }
                }

                @Override
                public void onFail(Throwable t, int code) {
                    callback.onFail(t);
                }
            };

            if (weatherCityModel.getCityId() == null) {
                WeatherLoader.getByCity(weatherCityModel.getCity(), callbackApi);
            } else {
                WeatherLoader.getById(weatherCityModel.getCityId(), callbackApi);
            }
        }

    }

    public List<WeatherCityDaysModel> getWeatherDays(WeatherCityModel city) throws SQLException {
        Dao<WeatherCityDaysModel, Long> cityDaysDao = WeatherCityDaysModel.getDao();
        return cityDaysDao.queryForEq(WeatherCityDaysModel.F_CITY, city);
    }

    public WeatherCityDaysModel getCurrentWeatherDays(WeatherCityModel city) throws SQLException {
        Dao<WeatherCityDaysModel, Long> cityDaysDao = WeatherCityDaysModel.getDao();

        QueryBuilder<WeatherCityDaysModel, Long> qb = cityDaysDao.queryBuilder();

        qb.where().eq(WeatherCityDaysModel.F_CITY, city);
        qb.orderBy(WeatherCityDaysModel.F_DATE, true);
        return qb.queryForFirst();
    }

    public boolean existsCity(String city) throws SQLException {
        Dao<WeatherCityModel, Long> cityDao = WeatherCityModel.getDao();
        List<WeatherCityModel> weatherCityModels = cityDao.queryForEq(WeatherCityModel.F_CITY, city);

        return weatherCityModels.size() > 0;
    }

    public boolean existsCity(long city) throws SQLException {
        Dao<WeatherCityModel, Long> cityDao = WeatherCityModel.getDao();
        List<WeatherCityModel> weatherCityModels = cityDao.queryForEq(WeatherCityModel.F_CITY_ID, city);

        return weatherCityModels.size() > 0;
    }


}
