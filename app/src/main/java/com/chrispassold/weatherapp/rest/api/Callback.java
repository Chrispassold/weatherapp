package com.chrispassold.weatherapp.rest.api;

public interface Callback {
    void onSuccess();

    void onFail(Throwable t);
}
