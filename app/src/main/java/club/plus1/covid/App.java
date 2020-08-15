package club.plus1.covid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Objects;

import club.plus1.covid.db.DB;
import club.plus1.covid.server.ServerAPI;
import club.plus1.covid.server.ServerConfig;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context app;
    public static DB db;
    public static ServerAPI api;

    public static int versionCode;
    public static String versionName;

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
        PackageInfo pInfo = null;
        try {
            PackageManager pm = getPackageManager();
            if (pm != null){
                pInfo = pm.getPackageInfo(getPackageName(), 0);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(getString(R.string.app_name), Objects.requireNonNull(e.getMessage()));
        }
        if (pInfo == null) {
            pInfo = new PackageInfo();
            pInfo.versionName = "0.0";
            pInfo.versionCode = 0;
        }
        versionName = pInfo.versionName;
        versionCode = pInfo.versionCode;

    }

    private void terminate(){
        DB.destroy();
        app = null;
        db = null;
        api = null;
    }
}
