package game.classificacoes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.classificacoes.models.Classificacao;
import game.classificacoes.models.ClassificacaoDAO;

@Database(entities = {Classificacao.class}, version = 1)
public abstract class ClassiDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS=4;
    private static volatile ClassiDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //
    public static ClassiDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ClassiDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ClassiDatabase.class, "notes_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    public abstract ClassificacaoDAO getClassiDao();
}