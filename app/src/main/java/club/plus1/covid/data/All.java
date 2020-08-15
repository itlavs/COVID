package club.plus1.covid.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity (tableName = "global")
public class All {

    @PrimaryKey
    @NonNull
    public String date;

    @SerializedName("NewConfirmed")
    @Expose
    public int newConfirmed;

    @SerializedName("TotalConfirmed")
    @Expose
    public int totalConfirmed;

    @SerializedName("NewDeaths")
    @Expose
    public int newDeaths;

    @SerializedName("TotalDeaths")
    @Expose
    public int totalDeaths;

    @SerializedName("NewRecovered")
    @Expose
    public int newRecovered;

    @SerializedName("TotalRecovered")
    @Expose
    public int totalRecovered;

    public All() {
        date = "";
    }

    @Ignore
    @NotNull
    @Override
    public String toString(){
        return "(!:" + totalConfirmed + "+" + newConfirmed + ", X:"
                + totalDeaths + "+" + newDeaths + ", V:" + totalRecovered + "+" + newRecovered + ")";
    }

}
