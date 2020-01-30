package ipp.estg.lei.cmu.trabalhopratico.medication.models;


import java.sql.Date;

public class MedicationModel {
    private static int idCount = 0;
    public final String id;
    public final String titulo;
    public final String medicamento;
    public final int numeroTomasDiarias;
    public final int periodoTomaDiaria;
    public final Date dataToma;
    private int quantidade;

    public MedicationModel(String titulo, String medicamento, int quantidade, Date dataToma) {
        this.id = "Meds" + idCount++;
        this.titulo = titulo;
        this.medicamento = medicamento;
        this.quantidade = quantidade;
        this.numeroTomasDiarias = 1;
        this.periodoTomaDiaria = 0;
        this.dataToma = dataToma;
    }

    public MedicationModel(String titulo, String medicamento, int quantidade,
                           int numeroTomasDiarias, int periodoTomaDiaria, Date dataToma) {
        this.id = "Meds" + idCount++;
        this.titulo = titulo;
        this.medicamento = medicamento;
        this.quantidade = quantidade;
        this.numeroTomasDiarias = numeroTomasDiarias;
        this.periodoTomaDiaria = periodoTomaDiaria;
        this.dataToma = dataToma;
    }

    public boolean tomarMedicacao(int quantidadeTomada) {
        if ((quantidade - quantidadeTomada) < 0) {
            return false;
        } else {
            quantidade -= quantidadeTomada;
            return true;
        }
    }

    public void adicionarStock(int quantidade) {
        this.quantidade += quantidade;
    }

    public int getQuantidadeAtualStock() {
        return quantidade;
    }

    @Override
    public String toString() {
        String s = "titulo: " + titulo + "; nomeMedicamento: " + medicamento + "; quantidadeAtual: "
                + quantidade + "; numTomasDiarias: " + numeroTomasDiarias
                + (numeroTomasDiarias != 1 ? "; periodoTomaDiaria: " + periodoTomaDiaria : "")
                + "; tomarAte: " + dataToma.toString();
        return s;
    }
}
