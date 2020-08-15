package club.plus1.covid.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "countries")
public class Detail {

    @PrimaryKey
    @SerializedName("CountryCode")
    @Expose
    @NonNull
    public String countryCode;

    @SerializedName("Country")
    @Expose
    public String country;

    @SerializedName("Slug")
    @Expose
    public String slug;

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

    @SerializedName("Date")
    @Expose
    public String date;

    @Ignore
    @NotNull
    @Override
    public String toString(){
        return country + " (!:" + totalConfirmed + "+" + newConfirmed + ", X:"
                + totalDeaths + "+" + newDeaths + ", V:" + totalRecovered + "+" + newRecovered + ")";
    }

    public Detail()
    {
        countryCode = "";
    }

    @Ignore
    public Detail(All all){
        country = "Все случаи";
        countryCode = "all";
        slug = "все";
        if (all != null){
            newConfirmed = all.newConfirmed;
            totalConfirmed = all.totalConfirmed;
            newRecovered = all.newRecovered;
            totalRecovered = all.totalRecovered;
            newDeaths = all.newDeaths;
            totalDeaths = all.totalDeaths;
            date = all.date;
        }
    }
}
