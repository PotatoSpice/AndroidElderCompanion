package ipp.estg.lei.cmu.trabalhopratico.game.classificacoes.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClassificacaoDAO {

    @Insert
    void insertClassificacao(Classificacao... classificacoes);

    @Delete
    void deleteClassificacao(Classificacao... classificacoes);

    @Query("SELECT * FROM Classificacao")
    List<Classificacao> loadAllClassificaoes();

    @Query("SELECT COUNT(*) FROM Classificacao")
    int getCount();

    @Query("SELECT * FROM Classificacao order by Classificacao.points desc limit 1")
    Classificacao loadTopClassificacao();

    @Query("SELECT * FROM Classificacao where dataScore = :todayDate order by Classificacao.points desc limit 1")
    Classificacao loadTopClassToday(String todayDate);
}

