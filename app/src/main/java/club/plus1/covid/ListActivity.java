package club.plus1.covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListAdapter adapter;
    List<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        list = new ArrayList<>();
        adapter = new ListAdapter(this, list);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setList();
    }

    void setList(){
        Item item = new Item();
        item.country = "Russia";
        item.newConfirmed = 5102;
        item.totalConfirmed = 907758;
        item.newDeaths = 129;
        item.totalDeaths = 15384;
        item.newRecovered = 582;
        item.totalRecovered = 716396;
        list.add(item);
    }
}