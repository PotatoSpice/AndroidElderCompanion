package ipp.estg.lei.cmu.trabalhopratico.dropdetection;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.SmsManager;

import ipp.estg.lei.cmu.trabalhopratico.R;

public class DropService extends Service {

    private MediaPlayer myPlayer;

    private SensorManager sensorManager;
    private SensorEventListener eventListener;
    private Context mContext;
    private final IBinder mBinder = new AlarmServiceBinder();

    public class AlarmServiceBinder extends Binder {
        public DropService getService() {
            return DropService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myPlayer = MediaPlayer.create(this, R.raw.nuke_alarm);
        myPlayer.setLooping(true);
        mContext = this;

        eventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0], y = event.values[1], z = event.values[2];
                double res = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
                if (res < 5 || res > 20) {
                    myPlayer.start();
                    sendAlertSMS();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAlarm();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public void startAlarm() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(eventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopAlarm() {
        sensorManager.unregisterListener(eventListener);
        if (myPlayer.isPlaying()) {
            myPlayer.stop();
        }
    }

    private void sendAlertSMS() {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, DropService.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(mContext.getSharedPreferences("config", Context.MODE_PRIVATE).getString("contacto_emergencia", ""), null, String.valueOf(R.string.drop_message), pi, null);
    }

}
