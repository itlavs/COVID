package club.plus1.covid.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import club.plus1.covid.data.Detail;

@Dao
public interface DetailDao {

    @Insert
    void createAll(List<Detail> list);

    @Query("SELECT * FROM countries")
    List<Detail> readAll();

    @Update
    void updateAll(List<Detail> list);

    @Query("DELETE FROM countries")
    void deleteAll();
}
