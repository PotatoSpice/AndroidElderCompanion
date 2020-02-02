package ipp.estg.lei.cmu.trabalhopratico.medication.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MedicationTakingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent request) {

        if (request.getAction() != null
                && request.getAction().equals("ipp.estg.lei.cmu.trabalhopratico.medication.action.TAKE_MEDICATION")) {

            // TODO REMOVER STOCK DA MEDICAÇÃO COM O ID request.getStringExtra("med_id");

        } else if (request.getAction() != null
                && request.getAction().equals("ipp.estg.lei.cmu.trabalhopratico.medication.action.DISMISS_MEDICATION")) {

            // TODO NOTIFICAR CONTACTO PRINCIPAL

        }
    }
}
