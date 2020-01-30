package ipp.estg.lei.cmu.trabalhopratico.medication.database;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ipp.estg.lei.cmu.trabalhopratico.medication.models.MedicationDao;
import ipp.estg.lei.cmu.trabalhopratico.medication.models.MedicationModel;

public class MedicationRepository {

    private MedicationDao mMedicationDao;
    private MutableLiveData<List<MedicationModel>> medicationList;

    public MedicationRepository(Application application) {
        MedicationDatabase db = MedicationDatabase.getDatabase(application);
        mMedicationDao = db.getMedicationDao();
        medicationList = new MutableLiveData<>(mMedicationDao.loadAllItems());
    }

    public MutableLiveData<List<MedicationModel>> getMedicationList() {
        return medicationList;
    }

    public void addMedicationItem(MedicationModel arg) {
        final MedicationModel temp = arg;
        MedicationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mMedicationDao.insertItem(temp);
            }
        });
        List<MedicationModel> tempList = medicationList.getValue();
        tempList.add(arg);
        medicationList.setValue(tempList);
    }

    public void deleteMedicationItem(MedicationModel arg) {
        final MedicationModel temp = arg;
        MedicationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mMedicationDao.insertItem(temp);
            }
        });
        List<MedicationModel> tempList = medicationList.getValue();
        tempList.remove(arg);
        medicationList.setValue(tempList);
    }

    public void deleteMedicationItemById(int id) {
        final int temp = id;
        MedicationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mMedicationDao.deleteItemById(temp);
            }
        });
        List<MedicationModel> tempList = medicationList.getValue();
        MedicationModel model = null;
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).id == id) model = tempList.get(i);
        }
        tempList.remove(model);
        medicationList.setValue(tempList);
    }

    public void deleteAllMedicationByName(String name) {
        final String temp = name;
        MedicationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mMedicationDao.deleteAllItemsByName(temp);
            }
        });
        List<MedicationModel> tempList = medicationList.getValue();
        MedicationModel model = null;
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).medicamento.equals(name)) model = tempList.get(i);
        }
        tempList.remove(model);
        medicationList.setValue(tempList);
    }
}
