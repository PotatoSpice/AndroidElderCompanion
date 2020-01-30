package ipp.estg.lei.cmu.trabalhopratico.medication.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicationDao {

    @Insert
    void insertItem(MedicationModel... notes);

    @Delete
    void deleteItem(MedicationModel... notes);

    @Query("DELETE FROM Medication WHERE id = :id")
    void deleteItemById(int id);

    @Query("DELETE FROM Medication WHERE medicamento = :name")
    void deleteAllItemsByName(String name);

    @Query("SELECT * FROM Medication")
    List<MedicationModel> loadAllItems();

    @Query("SELECT COUNT(*) FROM Medication")
    int getCount();

}