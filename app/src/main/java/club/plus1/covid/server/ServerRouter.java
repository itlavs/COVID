package club.plus1.covid.server;

import android.annotation.SuppressLint;
import android.content.Context;

import club.plus1.covid.ui.ListModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServerRouter {

    @SuppressLint("CheckResult")
    public static void summary(Context context, ListModel viewModel){
        ServerConfig.getApi()
                .summary()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        body -> viewModel.ok(context, body),
                        error ->viewModel.error(context, error));
    }
}
