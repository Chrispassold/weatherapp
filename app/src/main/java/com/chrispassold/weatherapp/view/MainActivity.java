package com.chrispassold.weatherapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.chrispassold.weatherapp.R;
import com.chrispassold.weatherapp.adapter.WeatherMainAdapter;
import com.chrispassold.weatherapp.rest.loader.WeatherLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rvList;
    WeatherMainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvList = findViewById(R.id.rv_main);
        setupList();
    }

    private void openAddCity() {
        final AddCityDialog addCityDialog = new AddCityDialog(this);
        addCityDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void refreshList(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                openAddCity();
                return true;
            case R.id.refresh:
                Toast.makeText(this, "refresh", Toast.LENGTH_LONG).show();
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
