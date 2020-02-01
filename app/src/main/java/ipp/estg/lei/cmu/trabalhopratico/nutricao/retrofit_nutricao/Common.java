package ipp.estg.lei.cmu.trabalhopratico.nutricao.retrofit_nutricao;


public class Common {

    private static final String URL = "https://world.openfoodfacts.org";

    public static DataService getDataService() {
        return RetrofitClient.getCliente(URL).create(DataService.class);
    }
}


