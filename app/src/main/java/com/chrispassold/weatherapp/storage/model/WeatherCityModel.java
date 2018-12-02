package com.chrispassold.weatherapp.storage.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "weather_city")
public class WeatherCityModel {

    public static final String F_CITY = "city";
    public static final String F_LAST_UPDATE = "lastUpdate";

    public WeatherCityModel() {
    }

    public WeatherCityModel(String city, Date lastUpdate) {
        this.city = city;
        this.lastUpdate = lastUpdate;
    }

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(columnName = F_CITY, index = true, unique = true)
    private String city;

    @DatabaseField(columnName = F_LAST_UPDATE)
    private Date lastUpdate;

//    @DatabaseField(canBeNull = true, foreign = true)
//    private WeatherCityDaysModel weatherDays;i


    public Long getId() {
        return id;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
