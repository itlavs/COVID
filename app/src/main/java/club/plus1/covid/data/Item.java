package club.plus1.covid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class Item {

    @SerializedName("Country")
    @Expose
    public String country;

    @SerializedName("CountryCode")
    @Expose
    public String countryCode;

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

    @NotNull
    @Override
    public String toString(){
        return country;
    }
}
