package ipp.estg.lei.cmu.trabalhopratico.medicacao.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ipp.estg.lei.cmu.trabalhopratico.R;
import ipp.estg.lei.cmu.trabalhopratico.medicacao.models.MedicationModel;

import java.util.List;

public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.MedicationViewHolder> {

    private Context mContext;
    private final List<MedicationModel> medicationList;

    public MedicationListAdapter(Context context, List<MedicationModel> items) {
        mContext = context;
        medicationList = items;
    }

    @Override
    public MedicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_medication, parent, false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MedicationViewHolder holder, int position) {
        MedicationModel med = medicationList.get(position);
        holder.mItem = med;
        holder.mTitulo.setText(med.titulo);
        holder.mNome.setText(med.medicamento);
        holder.mQuantidade.setText(mContext.getString(R.string.medication_quantity, med.getQuantidadeAtualStock()));
        holder.mDataToma.setText(mContext.getString(R.string.medication_enddate, med.dataToma.toString()));
        holder.mNumeroTomasDiarias.setText(mContext.getString(R.string.medication_dailytakes, med.numeroTomasDiarias));
        holder.mPeriodoTomaDiaria.setText(mContext.getString(R.string.medication_dailyperiod, med.periodoTomaDiaria));
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    public class MedicationViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mTitulo;
        public final TextView mNome;
        public final TextView mQuantidade;
        public final TextView mDataToma;
        public final TextView mNumeroTomasDiarias;
        public final TextView mPeriodoTomaDiaria;
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
        }

    }
}
