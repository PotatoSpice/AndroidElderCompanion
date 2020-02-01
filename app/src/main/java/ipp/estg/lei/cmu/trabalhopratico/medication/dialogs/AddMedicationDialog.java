package ipp.estg.lei.cmu.trabalhopratico.medication.dialogs;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import ipp.estg.lei.cmu.trabalhopratico.medication.receivers.MedicationNotifyReceiver;
import ipp.estg.lei.cmu.trabalhopratico.medication.viewmodel.MedicationViewModel;

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
                String str = year + "-" + (month + 1) + "-" + dayOfMonth;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                            MedicationModel model = new MedicationModel(title.getText().toString(),
                                    medication.getText().toString(),
                                    Integer.parseInt(quantity.getText().toString()),
                                    Integer.parseInt(dailyStart.getText().toString()),
                                    Integer.parseInt(dailyTakes.getText().toString()),
                                    Integer.parseInt(dailyPeriod.getText().toString()),
                                    pickedDate);
                            startAlarm(getContext(), model);
                            liveData.addMedicationItem(model);
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

        if (dt != 1) {
            int dp;
            try {
                dp = Integer.parseInt(dailyPeriod.getText().toString());
            } catch (NumberFormatException exc) {
                dp = -1;
            }
            if (dp > 24) {
                dailyPeriod.setError("Periodo de toma não deve ser superior a 24 horas");
                valid = false;
            } else if (dt != 1 && dt * dp + ds > 24) {
                dailyPeriod.setError("Numero, período e início de toma da medicação excedem 24 horas");
                valid = false;
            } else
                dailyPeriod.setError(null);
        } else {
            dailyPeriod.setText("0");
        }

        int q;
        try {
            q = Integer.parseInt(quantity.getText().toString());
        } catch (NumberFormatException exc) { q = -1; }
        if (q < 1) {
            quantity.setError("Quantidade de medicamentos deve ser maior que 1 (um)");
            valid = false;
        } else
            quantity.setError(null);

        if (pickedDate.before(new Date(System.currentTimeMillis()))) {
            pickDate.setError("Data não deve ser inferior ou igual à data atual");
            valid = false;
        } else
            pickDate.setError(null);

        return valid;
    }

    private void startAlarm(Context context, MedicationModel extra) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, extra.horaInicioTomaDiaria);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(getContext(), MedicationNotifyReceiver.class);
        intent.setAction("ipp.estg.lei.cmu.trabalhopratico.medication.action.NOTIFY_ALARM");
        intent.putExtra("data_fim", extra.dataFim.getTime());
        intent.putExtra("titulo", extra.titulo);
        intent.putExtra("medicamento", extra.medicamento);
        intent.putExtra("numero_tomas", extra.numeroTomasDiarias);
        intent.putExtra("hora_inicio", extra.horaInicioTomaDiaria);
        intent.putExtra("periodo_toma", extra.periodoTomaDiaria);
        intent.putExtra("quantidade_stock", extra.quantidadeAtualStock);

        if (extra.numeroTomasDiarias == 1) {
            intent.putExtra("is_in_period", false);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        } else {
            intent.putExtra("is_in_period", true);
            intent.putExtra("takes_counter", 1);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            for (int i = 0; i < extra.numeroTomasDiarias - 1; i++) {
                calendar.add(Calendar.HOUR_OF_DAY, extra.periodoTomaDiaria);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
            intent.putExtra("is_in_period", false);
            calendar.add(Calendar.HOUR_OF_DAY, extra.periodoTomaDiaria);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

}
