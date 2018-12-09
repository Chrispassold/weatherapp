package com.chrispassold.weatherapp.storage.model;

import com.chrispassold.weatherapp.storage.database.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.Date;

@DatabaseTable(tableName = "weather_city_days")
public class WeatherCityDaysModel {

    public static final String F_CITY = "city";
    public static final String F_DATE = "date";
    public static final String F_TEMP = "temp";
    public static final String F_TEMP_MIN = "tempMin";
    public static final String F_TEMP_MAX = "tempMax";
    public static final String F_MAIN = "main";
    public static final String F_DESCRIPTION = "description";
    public static final String F_ICON = "icon";

    @DatabaseField(index = true, unique = true, generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false, foreign = true, columnName = F_CITY)
    private WeatherCityModel city;

    @DatabaseField(columnName = F_DATE, canBeNull = false, dataType = DataType.DATE_LONG)
    private Date date;

    @DatabaseField(columnName = F_TEMP, canBeNull = false)
    private Double temp;

    @DatabaseField(columnName = F_TEMP_MIN, canBeNull = false)
    private Double tempMin;

    @DatabaseField(columnName = F_TEMP_MAX, canBeNull = false)
    private Double tempMax;

    @DatabaseField(columnName = F_MAIN, canBeNull = false)
    private String main;

    @DatabaseField(columnName = F_DESCRIPTION, canBeNull = false)
    private String description;

    @DatabaseField(columnName = F_ICON, canBeNull = false)
    private String icon;

    public WeatherCityDaysModel() {
    }

    public WeatherCityDaysModel(WeatherCityModel city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public WeatherCityModel getCity() {
        return city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static Dao<WeatherCityDaysModel, Long> getDao() throws SQLException {
        return DatabaseHelper.getDaoClass(WeatherCityDaysModel.class);
    }
}