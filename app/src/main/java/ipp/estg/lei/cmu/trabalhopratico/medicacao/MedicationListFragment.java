package ipp.estg.lei.cmu.trabalhopratico.medicacao;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ipp.estg.lei.cmu.trabalhopratico.R;
import ipp.estg.lei.cmu.trabalhopratico.medicacao.adapters.MedicationListAdapter;
import ipp.estg.lei.cmu.trabalhopratico.medicacao.database.MedicationDatabase;
import ipp.estg.lei.cmu.trabalhopratico.medicacao.models.MedicationModel;
import ipp.estg.lei.cmu.trabalhopratico.medicacao.viewmodels.MedicationViewModel;

public class MedicationListFragment extends Fragment {

    private View view;
    private MedicationListAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<MedicationModel> medicationList;

    private MedicationViewModel liveData;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MedicationListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MedicationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // https://stackoverflow.com/questions/53903762/viewmodelproviders-is-deprecated-in-1-1-0
                // instância da liveData relativa ao ViewModel com a lista de Medicação Registada
                liveData = new ViewModelProvider(getActivity()).get(MedicationViewModel.class);
            }
        });

        // lista de Medicação Registada atual
        medicationList = new ArrayList<>();

        // instância da base de dados para armazenamento de Medicação Registada
        // SUBSTITUIDO POR LiveData on Room ATRAVÉS DO REPOSITÓRIO
        // medicationDB = MedicationDatabase.getDatabase(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_medication_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            final Context context = view.getContext();

            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            mAdapter = new MedicationListAdapter(view.getContext(), medicationList, liveData);
            recyclerView.setAdapter(mAdapter);

            // Update recycler view every time the list gets updated
            liveData.getMedicationList().observe(getViewLifecycleOwner(), new Observer<List<MedicationModel>>() {
                @Override
                public void onChanged(@Nullable List<MedicationModel> medicationList) {
                    mAdapter.setMedicationItems(medicationList);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
        return view;
    }
}
