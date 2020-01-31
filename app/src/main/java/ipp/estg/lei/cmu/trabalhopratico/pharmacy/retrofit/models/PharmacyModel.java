package ipp.estg.lei.cmu.trabalhopratico.pharmacy.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PharmacyModel {

    @Expose
    @SerializedName("place_id")
    private int place_id;

    @Expose
    @SerializedName("license")
    private String license;

    @Expose
    @SerializedName("osm_type")
    private String osm_type;

    @Expose
    @SerializedName("osm_id")
    private long osm_id;

    @Expose
    @SerializedName("boundingbox")
    private List<String> boundingbox;

    @Expose
    @SerializedName("lat")
    private double latitude;

    @Expose
    @SerializedName("lon")
    private double longitude;

    @Expose
    @SerializedName("display_name")
    private String display_name;

    @Expose
    @SerializedName("class")
    private String classP;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("importance")
    private float importance;

    @Expose
    @SerializedName("icon")
    private String icon;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDisplay_name() {
        return display_name;
    }

}
