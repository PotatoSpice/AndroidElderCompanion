package game.classificacoes.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Classificacao {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String username;
    public int points;

    public Classificacao(String username, int points) {
        this.username = username;
        this.points = points;
    }

}
