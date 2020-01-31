package ipp.estg.lei.cmu.trabalhopratico.game.classificacoes.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Classificacao {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String dataScore;

    public String username;
    public int points;

    public Classificacao(String username, int points) {
        this.username = username;
        this.points = points;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dataScore= df.format(c.getTime());

    }

}
