package ipp.estg.lei.cmu.trabalhopratico.nutricao.classes_nutricao;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ipp.estg.lei.cmu.trabalhopratico.R;
import ipp.estg.lei.cmu.trabalhopratico.nutricao.retrofit_nutricao.Common;
import ipp.estg.lei.cmu.trabalhopratico.nutricao.retrofit_nutricao.DataService;
import ipp.estg.lei.cmu.trabalhopratico.nutricao.retrofit_nutricao.FoodModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodTask extends AppCompatActivity {

    DataService mService;
    TextView txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_task);

        mService = Common.getDataService();

        txtData = (TextView) findViewById(R.id.dataTextView);

        getDataAddress();
    }

    private void getDataAddress() {

        mService.getFood("api/v0/prodfuct/" + String.valueOf(ScanCodeActivity.resultado) + ".json").enqueue(new Callback<FoodModel>() {
            @Override
            public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                txtData.setText("Energy Unit: " + response.body().getEnergyUnit() + "\nEnergy Value:" + response.body().getEnergyValue() +
                        "\n\nFat Unit: " + response.body().getFatUnit() + "\nFat Value:" + response.body().getFatValue());
            }

            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {
                txtData.setText("Something Failed. Please try again.");
            }
        });
    }
}
