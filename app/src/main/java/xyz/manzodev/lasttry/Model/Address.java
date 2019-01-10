package xyz.manzodev.lasttry.Model;



import com.google.android.gms.maps.model.LatLng;

public class Address {
    int id;
    String textAddr;
    public LatLng latlng;

    public Address(String textAddr, LatLng latlng) {
        this.textAddr = textAddr;
        this.latlng = latlng;
    }

    public Address(int id, String textAddr, LatLng latlng) {
        this.id = id;
        this.textAddr = textAddr;
        this.latlng = latlng;
    }

    public Address() {
    }


    public String getTextAddr() {
        return textAddr;
    }
}
