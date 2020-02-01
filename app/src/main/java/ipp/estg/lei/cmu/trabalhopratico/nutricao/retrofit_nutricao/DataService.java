package ipp.estg.lei.cmu.trabalhopratico.nutricao.retrofit_nutricao;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DataService {

    @GET
    Call<FoodModel> getFood(@Url String url);

}
