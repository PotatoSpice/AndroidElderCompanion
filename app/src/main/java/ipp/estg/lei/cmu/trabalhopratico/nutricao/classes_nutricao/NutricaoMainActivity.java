package ipp.estg.lei.cmu.trabalhopratico.nutricao.classes_nutricao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ipp.estg.lei.cmu.trabalhopratico.R;


public class NutricaoMainActivity extends AppCompatActivity {

    public static TextView result_txt;
    Button btn_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutricao_main);


        result_txt = (TextView) findViewById(R.id.result_txt);
        btn_scan = (Button) findViewById(R.id.btn_scan);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }
        });
    }

}
