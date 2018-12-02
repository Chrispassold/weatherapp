package com.chrispassold.weatherapp.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.util.Log;
import android.widget.LinearLayout;

import com.chrispassold.weatherapp.rest.api.CallbackApi;
import com.chrispassold.weatherapp.rest.loader.WeatherLoader;
import com.chrispassold.weatherapp.storage.database.DatabaseHelper;
import com.chrispassold.weatherapp.storage.model.WeatherCityModel;
import com.chrispassold.weatherapp.storage.pojo.WeatherData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Date;

class AddCityDialog extends AlertDialog.Builder {

    final AppCompatEditText view;
    Runnable onAddNew;

    AddCityDialog(@NonNull Context context) {
        super(context);
        setTitle("Adicionar cidade");

        view = getView();

        setView(view);
        setPositiveButton();
        setNegativeButton();
    }

    public String getCity() {
        return String.valueOf(view.getText());
    }

    public void onAddNew(Runnable runnable) {
        onAddNew = runnable;
    }

    public void setPositiveButton() {
        setPositiveButton("Adicionar", (dialog, which) -> {
            try {
                WeatherLoader.getByCity(getCity(), new CallbackApi<WeatherData>() {
                    @Override
                    public void onSuccess(WeatherData data) {
                        Gson gson = new GsonBuilder().create();
                        String json = gson.toJson(data);
                        Log.i("tempo", json);
                    }

                    @Override
                    public void onFail(Throwable t) {
                        Log.e("tempo", t.getMessage());
                    }
                });

//                Dao<WeatherCityModel, Long> dao = DatabaseHelper.getDaoClass(WeatherCityModel.class);
//                dao.create(new WeatherCityModel(getCity(), new Date()));
            } finally {
                if (onAddNew != null)
                    onAddNew.run();

//                dialog.dismiss();
            }
        });
    }

    private void setNegativeButton() {
        setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
    }

    private AppCompatEditText getView() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        AppCompatEditText view = new AppCompatEditText(getContext());
        view.setMaxLines(1);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(100);

        view.setFilters(filterArray);
        view.setLayoutParams(lp);

        return view;
    }
}