package ipp.estg.lei.cmu.trabalhopratico.medicacao.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ipp.estg.lei.cmu.trabalhopratico.medicacao.database.MedicationRepository;
import ipp.estg.lei.cmu.trabalhopratico.medicacao.models.MedicationModel;

public class MedicationViewModel extends AndroidViewModel {

    private MedicationRepository mRepository;
    private MutableLiveData<List<MedicationModel>> medicationList;

    public MedicationViewModel(Application application) {
        super(application);
        mRepository = new MedicationRepository(application);
        medicationList = mRepository.getMedicationList();
    }

    public void addMedicationItem(MedicationModel arg) {
        mRepository.addMedicationItem(arg);
    }

    public void deleteMedicationItem(MedicationModel arg) {
        mRepository.deleteMedicationItem(arg);
    }

    public void deleteMedicationItemById(int arg) {
        mRepository.deleteMedicationItemById(arg);
    }

    public LiveData<List<MedicationModel>> getMedicationList() {
        return this.medicationList;
    }
}
