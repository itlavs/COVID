package club.plus1.covid.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

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

    public static ListModel getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ListModel(context);
        }
        return mInstance;
    }

    private ListModel(Context context)
    {
        list = new ArrayList<>();
        copy = new ArrayList<>();
        adapter = new ListAdapter(context, this);
        new Thread(() -> {
            list = App.db.detailDao().readAll();
            all = App.db.allDao().read();
            copy.addAll(list);
            ListAdapter.handler.sendEmptyMessage(0);
        }).start();
        ServerRouter.summary(context, this);
    }

    public void ok(Context context, ServerData data){
        list.clear();
        for (Detail detail : data.list){
            Log.d("COVID", detail.toString());
            list.add(detail);
        }
        all = data.all;
        all.date = data.date;
        list.add(0, new Detail(all));
        copy.addAll(list);
        new Thread(() -> {
            App.db.detailDao().deleteAll();
            App.db.detailDao().createAll(list);
            App.db.allDao().delete();
            App.db.allDao().create(all);
            ListAdapter.handler.sendEmptyMessage(0);
        }).start();
        Toast.makeText(context, R.string.success, Toast.LENGTH_LONG).show();
        adapter.notifyDataSetChanged();
    }

    public void error(Context context, Throwable error){
        Log.e("COVID", context.getString(R.string.error, error));
        Toast.makeText(context, context.getString(R.string.error, error), Toast.LENGTH_LONG).show();
    }
}
