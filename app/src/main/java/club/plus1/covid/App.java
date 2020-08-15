package club.plus1.covid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import club.plus1.covid.db.DB;
import club.plus1.covid.server.ServerAPI;
import club.plus1.covid.server.ServerConfig;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context app;
    public static DB db;
    public static ServerAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        terminate();
    }

    private void init(){
        app = this;
        db = DB.create(this);
        api = ServerConfig.getApi();
    }

    private void terminate(){
        DB.destroy();
        app = null;
        db = null;
        api = null;
    }
}
