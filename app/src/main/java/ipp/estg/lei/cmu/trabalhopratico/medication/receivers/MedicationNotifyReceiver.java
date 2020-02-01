package ipp.estg.lei.cmu.trabalhopratico.medication.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.sql.Date;
import java.util.Calendar;

import ipp.estg.lei.cmu.trabalhopratico.medication.models.MedicationModel;

public class MedicationNotifyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent request) {
        Date dataFim = new Date(request.getLongExtra("data_fim", System.currentTimeMillis()));
        boolean period = request.getBooleanExtra("is_in_period", false);
        String titulo = request.getStringExtra("titulo"),
                medicamento = request.getStringExtra("medicamento");
        int horaInicio = request.getIntExtra("hora_inicio", 0),
                numeroTomas = request.getIntExtra("numero_tomas", 0),
                periodoToma = request.getIntExtra("periodo_toma", 0),
                quantidadeAtual = request.getIntExtra("quantidade_atual", 0),
                takesCounter = request.getIntExtra("takesCounter", 1);

        if (request.getAction().equals("ipp.estg.lei.cmu.trabalhopratico.medication.action.NOTIFY_ALARM")) {
            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder nb = notificationHelper.getMedicationNotification(titulo, medicamento);
            notificationHelper.getManager()
                    .notify(NotificationHelper.getNewNotificationID(), nb.build());

            if (dataFim.after(new Date(System.currentTimeMillis()))) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, horaInicio);

                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }

                Intent intent = new Intent("ipp.estg.lei.cmu.trabalhopratico.medication.action.NOTIFY_ALARM");
                intent.putExtra("data_fim", dataFim);
                intent.putExtra("titulo", titulo);
                intent.putExtra("medicamento", medicamento);
                intent.putExtra("numero_tomas", numeroTomas);
                intent.putExtra("hora_inicio", horaInicio);
                intent.putExtra("periodo_toma", periodoToma);
                intent.putExtra("quantidade_stock", quantidadeAtual);

                if (!period) {
                    intent.putExtra("is_in_period", false);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                } else if (takesCounter < numeroTomas - 1) {
                    intent.putExtra("is_in_period", true);
                    intent.putExtra("takes_counter", takesCounter++);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    calendar.add(Calendar.HOUR_OF_DAY, periodoToma);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                } else {
                    intent.putExtra("is_in_period", false);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    calendar.add(Calendar.HOUR_OF_DAY, periodoToma);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                }
            }

        }
    }
}
