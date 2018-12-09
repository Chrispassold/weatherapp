package com.chrispassold.weatherapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.LinearLayout;

import com.chrispassold.weatherapp.exception.CityAlreadyExistsException;
import com.chrispassold.weatherapp.exception.CityNotFoundException;
import com.chrispassold.weatherapp.exception.WeatherException;
import com.chrispassold.weatherapp.rest.api.CallbackApi;
import com.chrispassold.weatherapp.rest.loader.WeatherLoader;
import com.chrispassold.weatherapp.storage.api.WeatherCityData;
import com.chrispassold.weatherapp.storage.control.WeatherControl;
import com.chrispassold.weatherapp.storage.model.WeatherCityModel;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

class AddCityDialog extends AlertDialog.Builder {

    final AppCompatEditText view;
    Runnable onAddNew;
    final WeatherControl control;

    AddCityDialog(@NonNull Context context) {
        super(context);
        setTitle("Adicionar cidade");

        control = new WeatherControl();
        view = getView();

        setView(view);
        setPositiveButton();
        setNegativeButton();
    }

    public String getCity() {
        return String.valueOf(view.getText()).trim();
    }

    private void setError(String message) {
        view.setError(message);
    }

    public void onAddNew(Runnable runnable) {
        onAddNew = runnable;
    }

    @Override
    public AlertDialog show() {
        final AlertDialog show = super.show();

        show.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            final Dao<WeatherCityModel, Long> dao;
            try {
                dao = WeatherCityModel.getDao();

                List<WeatherCityModel> weatherCityModels = dao.queryForEq(WeatherCityModel.F_CITY, getCity());

                if (weatherCityModels.size() > 0) {
                    throw new CityAlreadyExistsException();
                }

                WeatherLoader.getByCity(getCity(), new CallbackApi<WeatherCityData>() {
                    @Override
                    public void onSuccess(WeatherCityData data) {
                        try {

                            if (data.getCity() == null) {
                                throw new CityNotFoundException();
                            }

                            List<WeatherCityModel> weatherCityModels = dao.queryForEq(WeatherCityModel.F_CITY_ID, data.getCity().getId());

                            if (weatherCityModels.size() > 0) {
                                throw new CityAlreadyExistsException();
                            }

                            control.create(data);

                            if (onAddNew != null)
                                onAddNew.run();

                            show.dismiss();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            onFail(new SQLException("Occoreu um problema ao cadastrar cidade", e), 500);
                        } catch (WeatherException e) {
                            onFail(e, 500);
                        }
                    }

                    @Override
                    public void onFail(Throwable t, int code) {
                        String message = t.getMessage();

                        if (code == 404) {
                            message = "Cidade não encontrada";
                        }

                        setError(message);
                    }
                });


            } catch (SQLException e) {
                e.printStackTrace();
                setError(e.getMessage());
            } catch (WeatherException e) {
                e.printStackTrace();
                setError(e.getMessage());
            }
        });

        return show;
    }

    public void setPositiveButton() {
        setPositiveButton("Adicionar", (dialog, which) -> {
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
        view.setInputType(InputType.TYPE_CLASS_TEXT);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(100);

        view.setFilters(filterArray);
        view.setLayoutParams(lp);

        return view;
    }
}