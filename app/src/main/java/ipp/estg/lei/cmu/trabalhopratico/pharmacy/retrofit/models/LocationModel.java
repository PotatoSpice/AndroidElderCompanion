package ipp.estg.lei.cmu.trabalhopratico.pharmacy.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationModel {

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
    @SerializedName("lat")
    private double latitude;

    @Expose
    @SerializedName("lon")
    private double longitude;

    @Expose
    @SerializedName("display_name")
    private String display_name;

    @Expose
    @SerializedName("address")
    private AddressModel address;

    @Expose
    @SerializedName("boundingbox")
    private List<String> boundingbox;

    public AddressModel getAddress() {
        return this.address;
    }

    public Double getLocationLat() {
        return this.latitude;
    }

    public Double getLocationLon() {
        return this.longitude;
    }
}
