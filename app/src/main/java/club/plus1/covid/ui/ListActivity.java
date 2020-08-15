package club.plus1.covid.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.SearchView;

import org.jetbrains.annotations.NotNull;

import club.plus1.covid.R;

public class ListActivity extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;
    ListModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        model = ListModel.getInstance(this);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(model.adapter);

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        });
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        model.update();
    }
}