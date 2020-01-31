package ipp.estg.lei.cmu.trabalhopratico.pharmacy.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressModel {

    @Expose
    @SerializedName("city_district")
    private String city_district;

    @Expose
    @SerializedName("city")
    private String city;

    @Expose
    @SerializedName("town")
    private String town;

    @Expose
    @SerializedName("county")
    private String county;

    @Expose
    @SerializedName("state_district")
    private String state_district;

    @Expose
    @SerializedName("state")
    private String state;

    @Expose
    @SerializedName("postcode")
    private String postcode;

    @Expose
    @SerializedName("country")
    private String country;

    @Expose
    @SerializedName("country_code")
    private String country_code;

    public String getLocationName() {
        if(this.town != null){
            return this.town;
        }

        return this.county;
    }

}
