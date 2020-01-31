package ipp.estg.lei.cmu.trabalhopratico.pharmacy.retrofit;

import java.util.List;

import ipp.estg.lei.cmu.trabalhopratico.pharmacy.retrofit.models.LocationModel;
import ipp.estg.lei.cmu.trabalhopratico.pharmacy.retrofit.models.PharmacyModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenStreetMapAPI {

    @GET("/reverse")
    Call<LocationModel> getLocationDetails(@Query("format") String format,
                                           @Query("lat") Double lat, @Query("lon") Double lon);

    @GET("/search")
    Call<List<PharmacyModel>> getLocationPharmacies( @Query("q") String query, @Query("format") String format);

}
