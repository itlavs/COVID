package club.plus1.covid.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import club.plus1.covid.R;

public class ListActivity extends AppCompatActivity {

    ListModel model;
    RecyclerView recyclerView;
    SearchView searchView;
    TextView textCountry;
    TextView textConfirmed;
    TextView textDeaths;
    TextView textRecovered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        model = ListModel.getInstance(this);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(model.adapter);

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(searchListener);

        textCountry = findViewById(R.id.textCountry);
        textCountry.setOnClickListener(countryClickListener);
        textConfirmed = findViewById(R.id.textConfirmed);
        textConfirmed.setOnClickListener(confirmedClickListener);
        textDeaths = findViewById(R.id.textDeaths);
        textDeaths.setOnClickListener(deathsClickListener);
        textRecovered = findViewById(R.id.textRecovered);
        textRecovered.setOnClickListener(recoveredClickListener);
        model.setAllSort("country", 2,
                textCountry, textConfirmed, textDeaths, textRecovered);
    }

    SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            model.adapter.filter(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            model.adapter.filter(newText);
            return true;
        }
    };

    View.OnClickListener countryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (model.countrySort == 2)
                model.countrySort = -2;
            else
                model.countrySort = 2;
            model.confirmedSort = 0;
            model.deathsSort = 0;
            model.recoveredSort = 0;
            model.setAllSort("country", model.countrySort,
                    textCountry, textConfirmed, textDeaths, textRecovered);
        }
    };

    View.OnClickListener confirmedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            model.countrySort = 0;
            if (model.confirmedSort == -1)
                model.confirmedSort = 1;
            else
                model.confirmedSort = -1;
            model.deathsSort = 0;
            model.recoveredSort = 0;
            model.setAllSort("totalConfirmed", model.confirmedSort,
                    textCountry, textConfirmed, textDeaths, textRecovered);
        }
    };

    View.OnClickListener deathsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            model.countrySort = 0;
            model.confirmedSort = 0;
            if (model.deathsSort == -1)
                model.deathsSort = 1;
            else
                model.deathsSort = -1;
            model.recoveredSort = 0;
            model.setAllSort("totalDeaths", model.deathsSort,
                    textCountry, textConfirmed, textDeaths, textRecovered);
        }
    };

    View.OnClickListener recoveredClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            model.countrySort = 0;
            model.confirmedSort = 0;
            model.deathsSort = 0;
            if (model.recoveredSort == -1)
                model.recoveredSort = 1;
            else
                model.recoveredSort = -1;
            model.setAllSort("totalRecovered", model.recoveredSort,
                    textCountry, textConfirmed, textDeaths, textRecovered);
        }
    };

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        model.update();
    }
}