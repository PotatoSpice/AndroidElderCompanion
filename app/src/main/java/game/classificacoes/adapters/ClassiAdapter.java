package game.classificacoes.adapters;

import android.content.Context;

import java.util.List;

import game.classificacoes.database.ClassiDatabase;
import game.classificacoes.models.Classificacao;

public class ClassiAdapter {

    private Context mContext;
    private List<Classificacao> mClassi;
    private final ClassiDatabase mClassiDb;

    public ClassiAdapter(Context context, List<Classificacao> classi, ClassiDatabase notesDb) {
        mContext = context;
        this.mClassiDb = notesDb;
        this.mClassi = classi;
    }



}
