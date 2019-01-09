package xyz.manzodev.lasttry.Model;

import com.google.android.gms.maps.model.LatLng;

public class Address {
    public String textAddr;
    public LatLng latlng;

    public Address(String textAddr, LatLng latlng) {
        this.textAddr = textAddr;
        this.latlng = latlng;
    }

    public Address() {
    }

    public String getTextAddr() {
        return textAddr;
    }
}
