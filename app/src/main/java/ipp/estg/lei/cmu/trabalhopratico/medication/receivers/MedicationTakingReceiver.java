package ipp.estg.lei.cmu.trabalhopratico.medication.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.sql.Date;

public class MedicationTakingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent request) {
        Date dataFim = new Date(request.getLongExtra("data_fim", System.currentTimeMillis()));
        String titulo = request.getStringExtra("titulo"),
                medicamento = request.getStringExtra("medicamento");
        int horaInicio = request.getIntExtra("hora_inicio", 0),
                numeroTomas = request.getIntExtra("numero_tomas", 0),
                periodoToma = request.getIntExtra("periodo_toma", 0),
                quantidadeAtual = request.getIntExtra("quantidade_atual", 0);

        if (request.getAction() != null &&
                request.getAction().equals("ipp.estg.lei.cmu.trabalhopratico.medication.action.TAKE_MEDICATION")) {
            Toast.makeText(context, "Tomar Medicação", Toast.LENGTH_SHORT).show();

        } else if (request.getAction() != null
                && request.getAction().equals("ipp.estg.lei.cmu.trabalhopratico.medication.action.DISMISS_MEDICATION")) {
            Toast.makeText(context, "Cancelar Toma", Toast.LENGTH_SHORT).show();

        }
    }
}
