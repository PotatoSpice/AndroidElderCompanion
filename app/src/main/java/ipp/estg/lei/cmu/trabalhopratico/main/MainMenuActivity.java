package ipp.estg.lei.cmu.trabalhopratico.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ipp.estg.lei.cmu.trabalhopratico.LoginActivity;
import ipp.estg.lei.cmu.trabalhopratico.R;
import ipp.estg.lei.cmu.trabalhopratico.medication.viewmodel.MedicationViewModel;

public class MainMenuActivity extends AppCompatActivity {

    public static final String PREFERENCES_FILE_NAME = "pref";
    private ViewModelProvider self;

    private AppBarConfiguration mAppBarConfiguration;
    private EditText inputEmergencyContact;
    private MedicationViewModel liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_farmacy, R.id.navigation_nutrition, R.id.navigation_games)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // VERIFICAR E ADICIONAR O CONTACTO DE SEGURANÇA SE NAO EXISTIR (DIALOG)
        if (!getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
                .contains("contacto_emergencia"))
            createChangeMainContactDialog(this).show();

        // VERIFICAR REGISTOS DA MEDICAÇÃO E ATIVAR ALARMES
    }

    private AlertDialog createChangeMainContactDialog(Context context) {
        inputEmergencyContact = new EditText(context);
        inputEmergencyContact.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        String contacto_atual = getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
                .getString("contacto_emergencia", "<indefinido>");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Contacto atual: " + contacto_atual + "\nIntroduzir novo número:")
                .setCancelable(false)
                .setPositiveButton("Confirmar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                    }
                })
                .setView(inputEmergencyContact);
        final AlertDialog dialog = builder.create();

        // Set custom positive button listener
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String input = inputEmergencyContact.getText().toString();
                        if (input.isEmpty()) {
                            inputEmergencyContact.setError("Preenchimento Obrigatório");
                        } else if (!input.matches("^9[136][0-9]{7}$")) {
                            inputEmergencyContact.setError("Número Inválido");
                        } else {
                            inputEmergencyContact.setError(null);
                            getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
                                    .edit()
                                    .putString("contacto_emergencia", input)
                                    .apply();
                            Toast.makeText(getApplicationContext(), "Contacto adicionado com sucesso",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        return dialog;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_emergency_contact:
                createChangeMainContactDialog(this).show();
                return true;
            case R.id.action_signout:
                Intent logoutIntent = new Intent(this, LoginActivity.class);
                logoutIntent.setAction("ipp.estg.lei.cmu.trabalhopratico.SIGN_OUT_USER");
                startActivity(logoutIntent);
                finish(); // remover esta activity da stack para prevenir que o user volte para trás
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
