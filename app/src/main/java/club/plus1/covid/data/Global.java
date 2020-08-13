package club.plus1.covid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Global {
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
}
