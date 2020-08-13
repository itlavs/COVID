package club.plus1.covid.server;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerConfig {

    private static ServerConfig mInstance;
    private static ServerAPI serverApi;

    public static ServerConfig getInstance() {
        if (mInstance == null) {
            mInstance = new ServerConfig();
        }
        return mInstance;
    }

    private ServerConfig() {
        init();
    }

    void init() {
        try{
            OkHttpClient okHttpClient = getUnsafeOkHttpClient();

            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    //Базовая часть адреса
                    .baseUrl(ServerAPI.BASE_URL)
                    //Конвертер, необходимый для преобразования JSON'а в объекты
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //Поддержка RxJava
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

            Retrofit retrofit = retrofitBuilder.client(okHttpClient).build();

            //Создаем объект, при помощи которого будем выполнять запросы
            serverApi = retrofit.create(ServerAPI.class);

            Handler handler = new Handler(Looper.getMainLooper());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    public static ServerAPI getApi() {
        if (serverApi == null) {
            ServerConfig.getInstance();
        }
        return serverApi;
    }

    OkHttpClient getUnsafeOkHttpClient() throws KeyManagementException, NoSuchAlgorithmException {
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = getTrustToAllCerts();

        // Install the all-trusting trust manager
        final SSLContext sslContext = getSSLContext(trustAllCerts);

        // Create an ssl socket factory with our all-trusting manager
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
        builder.hostnameVerifier((hostname, session) -> true);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        return builder.addInterceptor(interceptor).build();
    }

    private static SSLContext getSSLContext(TrustManager[] trustAllCerts) throws NoSuchAlgorithmException, KeyManagementException {
        // Install the all-trusting trust manager
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        return sslContext;
    }

    private static TrustManager[] getTrustToAllCerts(){
        return new TrustManager[]{
                new X509TrustManager() {
                    @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}

                    @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}

                    @Override
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{};                    }
                }
        };
    }
}
