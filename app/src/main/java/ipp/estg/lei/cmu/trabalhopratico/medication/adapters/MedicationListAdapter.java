package ipp.estg.lei.cmu.trabalhopratico.medication.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.util.List;

import ipp.estg.lei.cmu.trabalhopratico.R;
import ipp.estg.lei.cmu.trabalhopratico.medication.models.MedicationModel;
import ipp.estg.lei.cmu.trabalhopratico.medication.viewmodel.MedicationViewModel;

public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.MedicationViewHolder> {

    private Context mContext;
    private List<MedicationModel> medicationList;
    private MedicationViewModel liveData;

    public MedicationListAdapter(Context context, List<MedicationModel> items, MedicationViewModel ld) {
        mContext = context;
        medicationList = items;
        liveData = ld;
    }

    @Override
    public MedicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_medication_item, parent, false);

        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MedicationViewHolder holder, int position) {
        MedicationModel med = medicationList.get(position);
        holder.mItem = med;
        holder.mTitulo.setText(med.titulo);
        holder.mNome.setText(med.medicamento);
        holder.mQuantidade.setText(mContext.getString(R.string.medication_quantity, med.quantidadeAtualStock));
        if (med.numeroTomasDiarias == 1) {
            holder.mNumeroTomasDiarias.setText(R.string.medication_dailytakes_one);
            holder.mPeriodoTomaDiaria.setVisibility(View.GONE);
        } else {
            holder.mNumeroTomasDiarias.setText(mContext.getString(R.string.medication_dailytakes, med.numeroTomasDiarias));
            holder.mPeriodoTomaDiaria.setVisibility(View.VISIBLE);
            holder.mPeriodoTomaDiaria.setText(mContext.getString(R.string.medication_dailyperiod, med.periodoTomaDiaria));
        }
        holder.mDataToma.setText(mContext.getString(R.string.medication_enddate, med.dataFim.toString()));
        holder.mHoraInicioTomaDiaria.setText(mContext.getString(R.string.medication_dailystart, med.horaInicioTomaDiaria));

        // Se ainda não tiver passado a data da toma da medicação registada (menor ou próprio dia)
        // Senão, o registo passou a validade acionando o botão para remover.
        if (med.dataFim.compareTo(new Date(System.currentTimeMillis())) > 0
                || DateUtils.isToday(med.dataFim.getTime())
                && !mContext.getSharedPreferences("conf", Context.MODE_PRIVATE).getBoolean("isDev", false)) {
            holder.mIconDelete.setVisibility(View.GONE);
            holder.mView.findViewById(R.id.meditem_detalhestoma)
                    .setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        } else {
            holder.mIconDelete.setVisibility(View.VISIBLE);
            holder.mView.findViewById(R.id.meditem_detalhestoma)
                    .setBackgroundColor(ContextCompat.getColor(mContext, R.color.meditem_alarm));
            final MedicationModel temp = med;
            holder.mIconDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setTitle(R.string.medication_remove_title)
                            .setMessage(R.string.medication_remove)
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    liveData.deleteMedicationItemById(temp.id);
                                    Toast.makeText(mContext, R.string.medication_remove_success,
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    dialog.show();
                }
            });
        }
    }

    public void setMedicationItems(List<MedicationModel> items) {
        medicationList = items;
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    public class MedicationViewHolder extends RecyclerView.ViewHolder {
      
        public final TextView mTitulo;
        public final TextView mNome;
        public final TextView mQuantidade;
        public final TextView mDataToma;
        public final TextView mNumeroTomasDiarias;
        public final TextView mPeriodoTomaDiaria;
        public final TextView mHoraInicioTomaDiaria;

        public final ImageView mIconDelete;

        public final View mView;
      
        public MedicationModel mItem;

        public MedicationViewHolder(View view) {
            super(view);
            mView = view;
            mTitulo = (TextView) view.findViewById(R.id.meditem_titulo);
            mNome = (TextView) view.findViewById(R.id.meditem_nome);
            mQuantidade = (TextView) view.findViewById(R.id.meditem_quantidade);
            mDataToma = (TextView) view.findViewById(R.id.meditem_dataToma);
            mNumeroTomasDiarias = (TextView) view.findViewById(R.id.meditem_numeroTomas);
            mPeriodoTomaDiaria = (TextView) view.findViewById(R.id.meditem_periodoToma);
            mHoraInicioTomaDiaria = view.findViewById(R.id.meditem_horaInicio);

            mIconDelete = view.findViewById(R.id.meditem_menu);
        }

    }
}
