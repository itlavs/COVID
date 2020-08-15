package club.plus1.covid.ui;

import android.content.Context;
import android.util.Log;
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
    public ListSorting sort;
    public String search;

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
        search = "";
        sort = new ListSorting(this, 2, 0, 0, 0);
        list = new ArrayList<>();
        copy = new ArrayList<>();
        adapter = new ListAdapter(context, this);
        new Thread(() -> {
            all = null;
            list.clear();
            copy.clear();
            all = App.db.allDao().read();
            list = App.db.detailDao().readAll();
            list.add(0, new Detail(all));
            copy.addAll(list);
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
            sort.sorting();
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
}
