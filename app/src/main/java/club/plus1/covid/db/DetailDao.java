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
    void create(Detail detail);

    @Insert
    void createAll(List<Detail> list);

    @Query("SELECT * FROM countries")
    List<Detail> readAll();

    @Query("SELECT * FROM countries ORDER BY " +
            "CASE WHEN :direction >= 0 THEN country END ASC, " +
            "CASE WHEN :direction < 0 THEN country END DESC")
    List<Detail> sortCountries(int direction);

    @Query("SELECT * FROM countries ORDER BY " +
            "CASE WHEN :direction >= 0 THEN totalConfirmed END ASC, " +
            "CASE WHEN :direction < 0 THEN totalConfirmed END DESC")
    List<Detail> sortConfirmed(int direction);

    @Query("SELECT * FROM countries ORDER BY " +
            "CASE WHEN :direction >= 0 THEN totalDeaths END ASC, " +
            "CASE WHEN :direction < 0 THEN totalDeaths END DESC")
    List<Detail> sortDeaths(int direction);

    @Query("SELECT * FROM countries ORDER BY " +
            "CASE WHEN :direction >= 0 THEN totalRecovered END ASC, " +
            "CASE WHEN :direction < 0 THEN totalRecovered END DESC")
    List<Detail> sortRecovered(int direction);

    @Update
    void updateAll(List<Detail> list);

    @Query("DELETE FROM countries")
    void deleteAll();
}
