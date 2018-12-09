package com.chrispassold.weatherapp.storage.api;

import com.chrispassold.weatherapp.storage.pojo.City;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherCityData implements Serializable {

    private Integer cod;
    private int cnt;
    private City city;
    private List<com.chrispassold.weatherapp.storage.pojo.List> list = new ArrayList<>();

    public Integer getCod() {
        return cod;
    }

    public int getCnt() {
        return cnt;
    }

    public City getCity() {
        return city;
    }

    public List<com.chrispassold.weatherapp.storage.pojo.List> getList() {
        return list;
    }
}
