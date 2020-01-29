package ipp.estg.lei.cmu.trabalhopratico.medicacao.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ipp.estg.lei.cmu.trabalhopratico.medicacao.models.MedicationDao;
import ipp.estg.lei.cmu.trabalhopratico.medicacao.models.MedicationModel;

@Database(entities = {MedicationModel.class}, version = 1, exportSchema = false)
public abstract class MedicationDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    private static volatile MedicationDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MedicationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MedicationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MedicationDatabase.class, "medication_database.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    public abstract MedicationDao getMedicationDao();
}