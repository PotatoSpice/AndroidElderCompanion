package ipp.estg.lei.cmu.trabalhopratico.nutricao.classes_nutricao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ipp.estg.lei.cmu.trabalhopratico.R;

public class ClasseIntermediaria extends AppCompatActivity {

    Button btn_scan, btn_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classe_intermediaria);

        btn_data = (Button) findViewById(R.id.btn_data);
        btn_scan = (Button) findViewById(R.id.btn_scan);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClasseIntermediaria.this, ScanCodeActivity.class));
            }
        });

        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClasseIntermediaria.this, FoodTask.class));
            }
        });
    }
}
