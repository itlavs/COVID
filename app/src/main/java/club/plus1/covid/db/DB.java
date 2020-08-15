package club.plus1.covid.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import club.plus1.covid.data.All;
import club.plus1.covid.data.Detail;

@Database(entities = {All.class, Detail.class}, version = 3)
public abstract class DB extends RoomDatabase {

    public abstract AllDao allDao();
    public abstract DetailDao detailDao();

    static DB mInstance;

    public static DB create(Context context) {
        if (context.getApplicationContext() == null){
            return null;
        }
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), DB.class, "db")
                    .fallbackToDestructiveMigration()
                    .build();
        } else {
            return null;
        }
        return mInstance;
    }

    public static void destroy() {
        mInstance = null;
    }
}
