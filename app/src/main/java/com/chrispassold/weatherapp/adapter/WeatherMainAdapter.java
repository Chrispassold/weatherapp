package com.chrispassold.weatherapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chrispassold.weatherapp.R;
import com.chrispassold.weatherapp.WeatherAppApplication;
import com.chrispassold.weatherapp.adapter.holder.WeatherMainHolder;
import com.chrispassold.weatherapp.storage.model.WeatherCityDaysModel;
import com.chrispassold.weatherapp.storage.model.WeatherCityModel;
import com.chrispassold.weatherapp.storage.repository.WeatherRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class WeatherMainAdapter extends RecyclerView.Adapter<WeatherMainHolder> {

    private final List<WeatherCityModel> mWeatherList;
    private final WeatherRepository repository;

    public WeatherMainAdapter(ArrayList<WeatherCityModel> weatherList) {
        mWeatherList = weatherList;
        repository = new WeatherRepository();
    }

    @NonNull
    @Override
    public WeatherMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherMainHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherMainHolder holder, int position) {
        WeatherCityModel weather = mWeatherList.get(position);
        holder.getTitle().setText(String.format(Locale.getDefault(), "%s",
                weather.getCity()
        ));

        try {
            WeatherCityDaysModel weatherDays = repository.getCurrentWeatherDays(weather);
            Glide.with(WeatherAppApplication.getContext()).load(String.format("http://openweathermap.org/img/w/%s.png", weatherDays.getIcon())).into(holder.getWeatherIcon());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mWeatherList != null ? mWeatherList.size() : 0;
    }

    /**
     * Método publico chamado para atualziar a lista.
     *
     * @param weather Novo objeto que será incluido na lista.
     */
    public void updateList(List<WeatherCityModel> weather) {
        mWeatherList.clear();
        mWeatherList.addAll(weather);
        notifyDataSetChanged();
    }

    // Método responsável por inserir um novo item na lista
    // e notificar que há novos itens.
    private void insertItem(WeatherCityModel weather) {
        mWeatherList.add(weather);
        notifyItemInserted(getItemCount());
    }

    // Método responsável por remover um item da lista.
    private void removerItem(int position) {
        mWeatherList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mWeatherList.size());
    }
}
