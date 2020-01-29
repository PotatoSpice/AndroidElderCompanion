package ipp.estg.lei.cmu.trabalhopratico.medicacao.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MedicationViewModel extends ViewModel {

    private MutableLiveData<List<MedicationModel>> medicationList;

    public MedicationViewModel() {
        medicationList = new MutableLiveData<>();
    }

    public void setMedicationList(List<MedicationModel> arg) {
        this.medicationList.setValue(arg);
    }

    public void setMedicationItem(int position, MedicationModel arg) {
        List<MedicationModel> temp = medicationList.getValue();
        temp.set(position, arg);
        this.medicationList.setValue(temp);
    }

    public LiveData<List<MedicationModel>> getMedicationList() {
        return this.medicationList;
    }
}
