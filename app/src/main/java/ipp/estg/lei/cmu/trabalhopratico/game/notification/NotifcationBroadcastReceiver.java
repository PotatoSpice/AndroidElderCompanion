package ipp.estg.lei.cmu.trabalhopratico.game.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotifcationBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationClass notificationHelper = new NotificationClass(context);
            notificationHelper.createNotification();
        }
}
