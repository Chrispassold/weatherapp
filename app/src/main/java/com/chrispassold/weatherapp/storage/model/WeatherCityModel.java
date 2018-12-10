package com.chrispassold.weatherapp.storage.model;

import com.chrispassold.weatherapp.storage.database.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

@DatabaseTable(tableName = "weather_city")
public class WeatherCityModel {

    public static final String F_CITY = "city";
    public static final String F_CITY_ID = "city_id";
    public static final String F_LAST_UPDATE = "lastUpdate";

    public WeatherCityModel() {
    }

    public WeatherCityModel(String city, Date lastUpdate) {
        this.city = city;
        this.lastUpdate = lastUpdate;
    }

    @DatabaseField(index = true, unique = true, generatedId = true)
    private Long id;

    @DatabaseField(columnName = F_CITY, index = true, unique = true)
    private String city;

    @DatabaseField(columnName = F_CITY_ID, index = true, unique = true)
    private Long cityId;

    @DatabaseField(columnName = F_LAST_UPDATE, dataType = DataType.DATE_LONG)
    private Date lastUpdate;

    @ForeignCollectionField(eager = true)
    private Collection<WeatherCityDaysModel> days;

//    @DatabaseField(canBeNull = true, foreign = true)
//    private WeatherCityDaysModel weatherDays;i


    public Long getId() {
        return id;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCityId() {
        return cityId;
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

    public Collection<WeatherCityDaysModel> getDays() {
        return days;
    }

    public void setDays(Collection<WeatherCityDaysModel> days) {
        this.days = days;
    }

    public static Dao<WeatherCityModel, Long> getDao() throws SQLException {
        return DatabaseHelper.getDaoClass(WeatherCityModel.class);
    }
}
