package ipp.estg.lei.cmu.trabalhopratico.nutricao.classes_nutricao;

import android.os.Bundle;
import android.widget.ImageView;
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
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_task);

        mService = Common.getDataService();

        txtData = (TextView) findViewById(R.id.dataTextView);

        imageView = (ImageView) findViewById(R.id.nutrientIcon);

        getDataAddress();
    }

    private void getDataAddress() {


        mService.getFood("api/v0/prodfuct/" + String.valueOf(ScanCodeActivity.resultado) + ".json").enqueue(new Callback<FoodModel>() {
            @Override
            public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                String avisos = "\n\n";

                if (response.body().getSugarsValue() >= 10)
                    avisos = avisos + "Alert: High Sugar Level\n-diabetes\n";
                if (response.body().getSaltValue() >= 10)
                    avisos = avisos + "Alert: High Salt Level\n\n" + "-hypertension\n";
                if (response.body().getFatValue() >= 10)
                    avisos = avisos + "Alert: High Fat Level\n-\n" + "obesity\n";
                if (response.body().getSaturatedFatValue() >= 10)
                    avisos = avisos + "Alert: High Saturated Fat Level\n" +
                            "-cholesterol\n";

                txtData.setText("\nEnergy Value: " + response.body().getEnergyValue() + response.body().getEnergyUnit()
                        + "\nFat Value: " + response.body().getFatValue() + response.body().getFatUnit()
                        + "\nSugar Value: " + response.body().getSugarsValue() + response.body().getSugarsUnit()
                        + "\nSalt Value: " + response.body().getSaltValue() + response.body().getSaltUnit()
                        + "\nSaturated Fat Value: " + response.body().getSaturatedFatValue() + response.body().getSaturatedFatUnit()
                        + "\nProteins Value: " + response.body().getSaltValue() + response.body().getProteinsUnit()
                        + avisos);
            }

            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {
                txtData.setText("Something Failed. Please try again.");
            }
        });
    }
}
