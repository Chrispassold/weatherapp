package com.chrispassold.weatherapp.exception;

public class CityNotFoundException extends WeatherException {

    public CityNotFoundException() {
        super("Cidade não encontrada");
    }
}
