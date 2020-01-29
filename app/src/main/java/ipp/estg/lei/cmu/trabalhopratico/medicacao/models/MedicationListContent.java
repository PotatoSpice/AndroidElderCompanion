package ipp.estg.lei.cmu.trabalhopratico.medicacao.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicationListContent {

    public static final List<MedicationModel> ITEMS = new ArrayList<MedicationModel>();

    public static final Map<String, MedicationModel> ITEM_MAP = new HashMap<String, MedicationModel>();

    public static void addItem(MedicationModel item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static MedicationModel createMedication(String titulo, String medicamento, int quantidade, Date validade) {
        return new MedicationModel(titulo, medicamento, quantidade, validade);
    }

    public static MedicationModel createMedication(String titulo, String medicamento, int quantidade,
                                                   int numeroTomasDiarias, int periodoTomaDiaria, Date validade) {
        return new MedicationModel(titulo, medicamento, quantidade, numeroTomasDiarias, periodoTomaDiaria, validade);
    }
}
