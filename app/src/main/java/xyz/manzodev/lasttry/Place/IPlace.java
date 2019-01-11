package xyz.manzodev.lasttry.Place;

import com.google.android.gms.maps.model.LatLng;

import xyz.manzodev.lasttry.Model.Address;

public interface IPlace {
    void onLocationBack(LatLng latLng,String loc);
    void handleMode(int mode);

    void handleSearchData(String data);
    void handleSearchData(Address data);

    void showNearby(String title, LatLng position);
    void hideNearby();

    void getDirection(LatLng latLng);
}
