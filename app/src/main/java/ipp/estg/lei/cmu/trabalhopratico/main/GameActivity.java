package ipp.estg.lei.cmu.trabalhopratico.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

import ipp.estg.lei.cmu.trabalhopratico.R;

public class GameActivity extends AppCompatActivity implements GameEntranceFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) return;
            GameEntranceFragment inpFragment = new GameEntranceFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, inpFragment)
                    .commit();
        }
    }

    @Override
    public void startGame(){
       GamePlayFragment gamePlayFragment = new GamePlayFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, gamePlayFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void notificationAlarm() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);

        if (calendar.getTime().compareTo(new Date()) < 0) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Intent intent = new Intent(getApplicationContext(), NotifcationBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }

    }
}
