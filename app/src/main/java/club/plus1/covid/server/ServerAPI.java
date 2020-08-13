package club.plus1.covid.server;

import club.plus1.covid.data.ServerData;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ServerAPI {

    String BASE_URL = "https://api.covid19api.com/";

    @GET("summary")
    Observable<ServerData> summary();
}
