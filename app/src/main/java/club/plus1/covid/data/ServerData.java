package club.plus1.covid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerData {

    @SerializedName("Global")
    @Expose
    public All all;

    @SerializedName("Countries")
    @Expose
    public List<Detail> list;

    @SerializedName("Date")
    @Expose
    public String date;
}
