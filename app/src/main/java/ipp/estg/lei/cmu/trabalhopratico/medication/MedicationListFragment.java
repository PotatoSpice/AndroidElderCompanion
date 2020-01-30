package ipp.estg.lei.cmu.trabalhopratico.medication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Date;
import java.util.List;

import ipp.estg.lei.cmu.trabalhopratico.R;
import ipp.estg.lei.cmu.trabalhopratico.medication.adapters.MedicationListAdapter;
import ipp.estg.lei.cmu.trabalhopratico.medication.models.MedicationListContent;
import ipp.estg.lei.cmu.trabalhopratico.medication.models.MedicationModel;
import ipp.estg.lei.cmu.trabalhopratico.medication.models.MedicationViewModel;

public class MedicationListFragment extends Fragment {

    private View view;
    private MedicationListAdapter mAdapter;
    private RecyclerView recyclerView;
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
        // https://stackoverflow.com/questions/53903762/viewmodelproviders-is-deprecated-in-1-1-0
        liveData = new ViewModelProvider(getActivity()).get(MedicationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_medication_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mAdapter = new MedicationListAdapter(view.getContext(), MedicationListContent.ITEMS);

            recyclerView = (RecyclerView) view;
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(
                    new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            MedicationListContent.addItem(new MedicationModel("titulo teste",
                    "nome teste", 5, new Date(System.currentTimeMillis())));

            // Update recycler view every time the list gets updated
            liveData.getMedicationList().observe(getViewLifecycleOwner(), new Observer<List<MedicationModel>>() {
                @Override
                public void onChanged(@Nullable List<MedicationModel> heroList) {
                    mAdapter = new MedicationListAdapter(view.getContext(), MedicationListContent.ITEMS);
                    recyclerView.setAdapter(mAdapter);
                }
            });
        }
        return view;
    }
}
