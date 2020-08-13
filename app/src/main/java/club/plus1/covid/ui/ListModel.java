package club.plus1.covid.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import club.plus1.covid.R;
import club.plus1.covid.data.Item;
import club.plus1.covid.data.ServerData;
import club.plus1.covid.server.ServerRouter;

public class ListModel {

    @SuppressLint("StaticFieldLeak")
    private static ListModel mInstance;

    public Context context;
    public ListAdapter adapter;
    public List<Item> list;

    public static ListModel getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ListModel(context);
        }
        return mInstance;
    }

    private ListModel(Context context)
    {
        this.context = context;
        list = new ArrayList<>();
        initList();
        ServerRouter.summary(context, this);
        adapter = new ListAdapter(context, list);
    }

    public void ok(Context context, ServerData data){
        list.clear();
        for (LinkedTreeMap<String, Object> el : data.list){
            Item item = new Item();
            item.country = (String) el.get("Country");
            item.newConfirmed = ((Double) Objects.requireNonNull(el.get("NewConfirmed"))).intValue();
            item.totalConfirmed = ((Double) Objects.requireNonNull(el.get("TotalConfirmed"))).intValue();
            item.newDeaths = ((Double) Objects.requireNonNull(el.get("NewDeaths"))).intValue();
            item.totalDeaths = ((Double) Objects.requireNonNull(el.get("TotalDeaths"))).intValue();
            item.newRecovered = ((Double) Objects.requireNonNull(el.get("NewRecovered"))).intValue();
            item.totalRecovered = ((Double) Objects.requireNonNull(el.get("TotalRecovered"))).intValue();
            Log.d("COVID", item.toString());
            list.add(item);
        }
        adapter.notifyDataSetChanged();
    }

    public void error(Context context, Throwable error){
        Log.e("COVID", context.getString(R.string.error, error));
        Toast.makeText(context, context.getString(R.string.error, error), Toast.LENGTH_LONG).show();
    }

    void initList(){
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
