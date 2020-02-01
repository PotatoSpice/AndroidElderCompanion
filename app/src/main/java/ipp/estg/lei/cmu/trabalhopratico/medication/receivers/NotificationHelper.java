package ipp.estg.lei.cmu.trabalhopratico.medication.receivers;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import ipp.estg.lei.cmu.trabalhopratico.R;
import ipp.estg.lei.cmu.trabalhopratico.main.MainMenuActivity;
import ipp.estg.lei.cmu.trabalhopratico.medication.models.MedicationModel;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "alarmNotifID";
    public static final String channelName = "Alarm Notifications";

    private static ArrayList<Integer> ids = new ArrayList<>();

    public static int getNewNotificationID() {
        int id = 1;
        if (ids.size() > 1000) ids.clear();
        while (ids.contains(id)) id++;
        return id;
    }

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getMedicationNotification(String titulo, String medicamento) {
        Intent dismiss = new Intent(this, MedicationTakingReceiver.class);
        dismiss.setAction("ipp.estg.lei.cmu.trabalhopratico.medication.action.DISMISS_MEDICATION");
        Intent take = new Intent(this, MedicationTakingReceiver.class);
        dismiss.setAction("ipp.estg.lei.cmu.trabalhopratico.medication.action.TAKE_MEDICATION");
        return new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.ic_add_alert_white_24dp)
                .setContentTitle("Toma de Medicação!")
                .setContentText("Não se esqueça do lembrete " + titulo + ",\npara a medicação " + medicamento + "!")
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0))
                // se o utilizador der swipe na notificação
                .setDeleteIntent(PendingIntent.getBroadcast(this, 0,
                        dismiss, PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_notifications_black_24dp, "Tomar Medicação",
                        PendingIntent.getBroadcast(this, 0,
                                take, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public NotificationCompat.Builder getCancelationNotification(MedicationModel m) {
        return new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.ic_add_alert_white_24dp)
                .setContentTitle("Alarme de Medicação Desativado!")
                .setContentText("Notificações para " + m.titulo + " desativadas, ou a data limite atingida.")
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0))
                .addAction(R.drawable.ic_notifications_black_24dp, "Abrir Aplicação",
                        PendingIntent.getActivity(this, 0,
                                new Intent(this, MainMenuActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));
    }
}
