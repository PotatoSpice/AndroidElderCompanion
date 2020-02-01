package ipp.estg.lei.cmu.trabalhopratico.medication.models;

import android.os.Parcel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;

import ipp.estg.lei.cmu.trabalhopratico.medication.database.converters.DateConverter;

@Entity(tableName = "Medication")
public class MedicationModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @TypeConverters(DateConverter.class)
    public Date dataFim;

    public String titulo;
    public String medicamento;
    public int horaInicioTomaDiaria;
    public int numeroTomasDiarias;
    public int periodoTomaDiaria;
    public int quantidadeAtualStock;

    public MedicationModel(String titulo, String medicamento, int quantidadeAtualStock, int horaInicioTomaDiaria,
                           int numeroTomasDiarias, int periodoTomaDiaria, Date dataFim) {
        this.titulo = titulo;
        this.medicamento = medicamento;
        this.horaInicioTomaDiaria = horaInicioTomaDiaria;
        this.quantidadeAtualStock = quantidadeAtualStock;
        this.numeroTomasDiarias = numeroTomasDiarias;
        this.periodoTomaDiaria = periodoTomaDiaria;
        this.dataFim = dataFim;
    }

    protected MedicationModel(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        medicamento = in.readString();
        horaInicioTomaDiaria = in.readInt();
        numeroTomasDiarias = in.readInt();
        periodoTomaDiaria = in.readInt();
        quantidadeAtualStock = in.readInt();
    }

    @Override
    public String toString() {
        String s = "titulo: " + titulo + "; nomeMedicamento: " + medicamento + "; quantidadeAtual: "
                + quantidadeAtualStock + "; numTomasDiarias: " + numeroTomasDiarias
                + (numeroTomasDiarias != 1 ? "; periodoTomaDiaria: " + periodoTomaDiaria : "")
                + "; tomarAte: " + dataFim.toString();
        return s;
    }
}
