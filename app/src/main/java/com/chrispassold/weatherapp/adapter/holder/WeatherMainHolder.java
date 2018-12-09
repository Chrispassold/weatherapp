package com.chrispassold.weatherapp.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrispassold.weatherapp.R;

public class WeatherMainHolder extends RecyclerView.ViewHolder {

    private TextView title;

    private ImageView weatherIcon;

    public WeatherMainHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.weather_item_city);
        weatherIcon = itemView.findViewById(R.id.weather_item_icon);
    }

    public TextView getTitle() {
        return title;
    }

    public ImageView getWeatherIcon() {
        return weatherIcon;
    }
}
