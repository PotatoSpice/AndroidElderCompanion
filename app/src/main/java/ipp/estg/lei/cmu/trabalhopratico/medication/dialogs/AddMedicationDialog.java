package ipp.estg.lei.cmu.trabalhopratico.medication.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ipp.estg.lei.cmu.trabalhopratico.R;
import ipp.estg.lei.cmu.trabalhopratico.medication.database.MedicationDatabase;
import ipp.estg.lei.cmu.trabalhopratico.medication.models.MedicationModel;
import ipp.estg.lei.cmu.trabalhopratico.medication.viewmodels.MedicationViewModel;

public class AddMedicationDialog extends DialogFragment {

    private EditText title;
    private EditText medication;
    private EditText dailyTakes;
    private EditText dailyPeriod;
    private EditText dailyStart;
    private EditText quantity;
    private Button pickDate;
    private Date pickedDate;

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            java.util.Date date;
            try {
                String str = dayOfMonth + "/" + month + 1 + "/" + year;
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                date = format.parse(str);
                pickedDate = new Date(date.getTime());
            } catch (ParseException e) {}
            Toast.makeText(getContext(), "Data definida: " + pickedDate.toString(), Toast.LENGTH_LONG).show();
        }
    };
    private MedicationViewModel liveData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MedicationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                liveData = new ViewModelProvider(getActivity()).get(MedicationViewModel.class);
            }
        });
        pickedDate = new Date(System.currentTimeMillis());
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Registar Nova Medicação")
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();

        // Set custom positive button listener
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (validateData()) {
                            liveData.addMedicationItem(new MedicationModel(title.getText().toString(),
                                    medication.getText().toString(),
                                    Integer.parseInt(quantity.getText().toString()),
                                    Integer.parseInt(dailyStart.getText().toString()),
                                    Integer.parseInt(dailyTakes.getText().toString()),
                                    Integer.parseInt(dailyPeriod.getText().toString()),
                                    pickedDate));
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        LayoutInflater i = getActivity().getLayoutInflater();

        View v = i.inflate(R.layout.fragment_add_medication, null);

        title = v.findViewById(R.id.add_medication_title);
        medication = v.findViewById(R.id.add_medication_name);
        quantity = v.findViewById(R.id.add_medication_quantity);
        dailyStart = v.findViewById(R.id.add_medication_dailyStart);
        dailyTakes = v.findViewById(R.id.add_medication_dailyTakes);
        dailyPeriod = v.findViewById(R.id.add_medication_dailyPeriod);
        pickDate = v.findViewById(R.id.add_medication_endDate);

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(getContext(), datePickerListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dialog.setView(v);
        return dialog;
    }

    private boolean validateData() {
        boolean valid = true;

        if (title.getText().toString().isEmpty()) {
            title.setError("Preenchimento obrigatório");
            valid = false;
        } else
            title.setError(null);

        if (medication.getText().toString().isEmpty()) {
            medication.setError("Preenchimento obrigatório");
            valid = false;
        } else
            medication.setError(null);

        int dt;
        try {
            dt = Integer.parseInt(dailyTakes.getText().toString());
        } catch (NumberFormatException exc) { dt = -1; }
        if (dt < 1) {
            dailyTakes.setError("Toma da medicação deve fazer-se pelo menos uma vez diariamente");
            valid = false;
        } else
            dailyTakes.setError(null);

        int ds;
        try {
            ds = Integer.parseInt(dailyStart.getText().toString());
        } catch (NumberFormatException exc) { ds = -1; }
        if (ds > 24) {
            dailyTakes.setError("Hora de toma/início de toma não deve ser superior a 24 horas");
            valid = false;
        } else
            dailyTakes.setError(null);

        int dp;
        try {
            dp = Integer.parseInt(dailyPeriod.getText().toString());
        } catch (NumberFormatException exc) { dp = -1; }
        if (dp > 24) {
            dailyPeriod.setError("Periodo de toma não deve ser superior a 24 horas");
            valid = false;
        } else if (dt != 1 && dt * dp + ds > 24) {
            dailyPeriod.setError("Numero, período e início de toma da medicação excedem 24 horas");
            valid = false;
        } else
            dailyPeriod.setError(null);

        int q;
        try {
            q = Integer.parseInt(quantity.getText().toString());
        } catch (NumberFormatException exc) { q = -1; }
        if (q < 1) {
            quantity.setError("Quantidade de medicamentos deve ser maior que 1 (um)");
            valid = false;
        } else
            quantity.setError(null);

        if (pickedDate.compareTo(new Date(System.currentTimeMillis())) < 0) {
            pickDate.setError("Data não deve ser inferior ou igual à data atual");
            valid = false;
        } else
            pickDate.setError(null);

        return valid;
    }

}
