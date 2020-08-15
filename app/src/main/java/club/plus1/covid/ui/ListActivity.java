package club.plus1.covid.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import org.jetbrains.annotations.NotNull;

import club.plus1.covid.R;

public class ListActivity extends AppCompatActivity {

    ListModel model;
    RecyclerView recyclerView;
    SearchView searchView;

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

        model.sort.textCountry = findViewById(R.id.textCountry);
        model.sort.textCountry.setOnClickListener(countryClickListener);
        model.sort.textConfirmed = findViewById(R.id.textConfirmed);
        model.sort.textConfirmed.setOnClickListener(confirmedClickListener);
        model.sort.textDeaths = findViewById(R.id.textDeaths);
        model.sort.textDeaths.setOnClickListener(deathsClickListener);
        model.sort.textRecovered = findViewById(R.id.textRecovered);
        model.sort.textRecovered.setOnClickListener(recoveredClickListener);
        model.sort.country = 2;
        model.sort.sorting();
        model.sort.setIcons();
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        model.update();
    }

    SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            model.search = query;
            model.adapter.filter();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            model.search = newText;
            model.adapter.filter();
            return true;
        }
    };

    View.OnClickListener countryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            model.sort.changeSorting(ListSorting.COUNTRY);
        }
    };

    View.OnClickListener confirmedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            model.sort.changeSorting(ListSorting.TOTAL_CONFIRMED);
        }
    };

    View.OnClickListener deathsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            model.sort.changeSorting(ListSorting.TOTAL_DEATHS);
        }
    };

    View.OnClickListener recoveredClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            model.sort.changeSorting(ListSorting.TOTAL_RECOVERED);
        }
    };
}