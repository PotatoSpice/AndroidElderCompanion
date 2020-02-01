package ipp.estg.lei.cmu.trabalhopratico.nutricao.retrofit_nutricao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodModel {

    @SerializedName("sugars_value")//56.3
    @Expose
    private Double sugarsValue;

    @SerializedName("sugars_unit")//g
    @Expose
    private String sugarsUnit;

    @SerializedName("fat_value")//30.9
    @Expose
    private Double fatValue;

    @SerializedName("salt_value")//0.107
    @Expose
    private Double saltValue;

    @SerializedName("salt_unit")//g
    @Expose
    private String saltUnit;

    @SerializedName("fat_unit")//g
    @Expose
    private String fatUnit;

    @SerializedName("energy_value")//2334
    @Expose
    private String energyValue;

    @SerializedName("energy_unit")//kj
    @Expose
    private String energyUnit;

    @SerializedName("saturated-fat_value")//10.6
    @Expose
    private Double saturatedFatValue;

    @SerializedName("saturated-fat_unit")//g
    @Expose
    private String saturatedFatUnit;

    @SerializedName("proteins_value")//6.3
    @Expose
    private Double proteinsValue;

    @SerializedName("proteins_unit")//g
    @Expose
    private String proteinsUnit;

    public Double getSugarsValue() {
        return sugarsValue;
    }

    public String getSugarsUnit() {
        return sugarsUnit;
    }

    public Double getFatValue() {
        return fatValue;
    }

    public Double getSaltValue() {
        return saltValue;
    }

    public String getSaltUnit() {
        return saltUnit;
    }

    public String getFatUnit() {
        return fatUnit;
    }

    public String getEnergyValue() {
        return energyValue;
    }

    public String getEnergyUnit() {
        return energyUnit;
    }

    public Double getSaturatedFatValue() {
        return saturatedFatValue;
    }

    public String getSaturatedFatUnit() {
        return saturatedFatUnit;
    }

    public Double getProteinsValue() {
        return proteinsValue;
    }

    public String getProteinsUnit() {
        return proteinsUnit;
    }
}
