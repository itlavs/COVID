package club.plus1.covid.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import club.plus1.covid.data.All;

@Dao
public interface AllDao {

    @Insert
    void create(All all);

    @Query("SELECT * FROM global")
    All read();

    @Update
    void update(All all);

    @Query("DELETE FROM global")
    void delete();
}
