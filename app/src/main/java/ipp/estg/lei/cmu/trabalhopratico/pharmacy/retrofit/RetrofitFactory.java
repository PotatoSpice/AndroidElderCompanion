package ipp.estg.lei.cmu.trabalhopratico.pharmacy.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    public static String URL = "https://nominatim.openstreetmap.org";

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static OpenStreetMapAPI getApi() {
        return getRetrofitInstance().create(OpenStreetMapAPI.class);
    }
}
