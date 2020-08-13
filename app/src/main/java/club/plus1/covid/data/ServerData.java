package club.plus1.covid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

public class ServerData {

    @SerializedName("Global")
    @Expose
    public LinkedTreeMap<String, Object> total;

    @SerializedName("Countries")
    @Expose
    public List<LinkedTreeMap<String, Object>> list;

    @SerializedName("Date")
    @Expose
    public String date;

}
