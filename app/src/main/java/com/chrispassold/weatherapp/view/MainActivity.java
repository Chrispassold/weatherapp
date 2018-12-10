package com.chrispassold.weatherapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chrispassold.weatherapp.R;
import com.chrispassold.weatherapp.adapter.WeatherMainAdapter;
import com.chrispassold.weatherapp.rest.api.Callback;
import com.chrispassold.weatherapp.storage.model.WeatherCityModel;
import com.chrispassold.weatherapp.storage.repository.WeatherRepository;
import com.chrispassold.weatherapp.util.Util;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rvList;
    ProgressBar progressBar;

    WeatherMainAdapter mAdapter;
    WeatherRepository repository;
    ArrayList<WeatherCityModel> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = new WeatherRepository();
        init();
        refreshList();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvList = findViewById(R.id.rv_main);
        setupList();

        progressBar = findViewById(R.id.progressBar);
    }

    private void showLoading() {
        runOnUiThread(() -> {
            rvList.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        });
    }

    private void hideLoading() {
        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            rvList.setVisibility(View.VISIBLE);
        });
    }

    private void refreshList() {
        try {

            showLoading();

            if (!Util.isNetworkAvailable()) {
                Dao<WeatherCityModel, Long> dao = WeatherCityModel.getDao();
                List<WeatherCityModel> weatherCityModels = dao.queryForAll();
                mAdapter.updateList(weatherCityModels);
                hideLoading();
                return;
            }

            repository.refreshCities(new Callback() {
                @Override
                public void onSuccess() {
                    try {
                        Dao<WeatherCityModel, Long> dao = WeatherCityModel.getDao();
                        List<WeatherCityModel> weatherCityModels = dao.queryForAll();
                        mAdapter.updateList(weatherCityModels);
                    } catch (SQLException e) {
                        onFail(e);
                    }finally {
                        hideLoading();
                    }
                }

                @Override
                public void onFail(Throwable t) {
                    hideLoading();
                    Toast.makeText(MainActivity.this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void openAddCity() {
        final AddCityDialog addCityDialog = new AddCityDialog(this);
        addCityDialog.onAddNew(this::refreshList);
        addCityDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                openAddCity();
                return true;
            case R.id.refresh:
                refreshList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);

        mAdapter = new WeatherMainAdapter(new ArrayList<>(0));
        rvList.setAdapter(mAdapter);

        rvList.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
