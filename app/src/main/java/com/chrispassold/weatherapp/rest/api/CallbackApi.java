package com.chrispassold.weatherapp.rest.api;

public interface CallbackApi<T> {

    void onSuccess(T data);

    void onFail(Throwable t);

}
