package com.chrispassold.weatherapp.exception;

public class CityAlreadyExistsException extends WeatherException {
    public CityAlreadyExistsException() {
        super("Cidade já cadastrada");
    }
}
