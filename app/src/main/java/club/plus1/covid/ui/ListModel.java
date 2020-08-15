package club.plus1.covid.ui;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import club.plus1.covid.App;
import club.plus1.covid.R;
import club.plus1.covid.data.Detail;
import club.plus1.covid.data.ServerData;
import club.plus1.covid.data.All;
import club.plus1.covid.server.ServerRouter;

public class ListModel {

    private static ListModel mInstance;

    public ListAdapter adapter;
    public List<Detail> list;
    public List<Detail> copy;
    public All all;
    public int countrySort;
    public int confirmedSort;
    public int deathsSort;
    public int recoveredSort;

    public static ListModel getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ListModel(context);
        }
        return mInstance;
    }

    public void update()
    {
        mInstance = new ListModel(adapter.context);
    }

    private ListModel(Context context)
    {
        countrySort = 2;
        confirmedSort = 0;
        deathsSort = 0;
        recoveredSort = 0;
        list = new ArrayList<>();
        copy = new ArrayList<>();
        adapter = new ListAdapter(context, this);
        new Thread(() -> {
            list.clear();
            copy.clear();
            all = null;
            list = App.db.detailDao().readAll();
            all = App.db.allDao().read();
            copy.addAll(list);
            list.add(0, new Detail(all));
            ListAdapter.handler.sendEmptyMessage(0);
        }).start();
        ServerRouter.summary(context, this);
    }

    public void ok(Context context, ServerData data){
        new Thread(() -> {
            App.db.allDao().delete();
            App.db.allDao().create(new All(data.all, data.date));
            all = App.db.allDao().read();

            App.db.detailDao().deleteAll();
            for (Detail detail : data.list){
                Log.d(context.getString(R.string.app_name), detail.toString());
                detail.country = Countries.getName(detail.countryCode);
                App.db.detailDao().create(detail);
            }
            list.clear();
            if (countrySort != 0)
                sortData("country", countrySort);
            if (confirmedSort != 0)
                sortData("totalConfirmed", confirmedSort);
            if (deathsSort != 0)
                sortData("totalDeaths", deathsSort);
            if (recoveredSort != 0)
                sortData("totalRecovered", recoveredSort);

            list.add(0, new Detail(all));
            copy.clear();
            copy.addAll(list);
            ListAdapter.handler.sendEmptyMessage(0);
        }).start();
        Toast.makeText(context, R.string.success, Toast.LENGTH_LONG).show();
        adapter.notifyDataSetChanged();
    }

    public void error(Context context, Throwable error){
        Log.e(context.getString(R.string.app_name), context.getString(R.string.error, error));
        Toast.makeText(context, context.getString(R.string.error, error), Toast.LENGTH_LONG).show();
    }

    private void sortData(String sortColumn, int direction){
        new Thread(() -> {
            list.clear();
            copy.clear();
            switch (sortColumn) {
                case "country":
                    list = App.db.detailDao().sortCountries(direction);
                    break;
                case "totalConfirmed":
                    list = App.db.detailDao().sortConfirmed(direction);
                    break;
                case "totalDeaths":
                    list = App.db.detailDao().sortDeaths(direction);
                    break;
                case "totalRecovered":
                    list = App.db.detailDao().sortRecovered(direction);
                    break;
                default:
                    list = App.db.detailDao().readAll();
                    break;
            }
            copy.addAll(list);
            list.add(0, new Detail(all));
            ListAdapter.handler.sendEmptyMessage(0);
        }).start();
    }

    public void setAllSort(String sortColumn, int direction,
            TextView country, TextView confirmed, TextView deaths, TextView recovered){
        sortData(sortColumn, direction);
        setSort(country, countrySort);
        setSort(confirmed, confirmedSort);
        setSort(deaths, deathsSort);
        setSort(recovered, recoveredSort);
    }

    void setSort(TextView textView, int sortType){
        int sortRes;
        if (sortType > 0)
            if (sortType == 2)
                sortRes = R.drawable.sort_az;
            else
                sortRes = R.drawable.sort_asc;
        else if (sortType < 0)
            if (sortType == -2)
                sortRes = R.drawable.sort_za;
            else
                sortRes = R.drawable.sort_desc;
        else
            sortRes = R.drawable.empty;
        textView.setCompoundDrawablesWithIntrinsicBounds(sortRes, 0, 0, 0);
    }
}
