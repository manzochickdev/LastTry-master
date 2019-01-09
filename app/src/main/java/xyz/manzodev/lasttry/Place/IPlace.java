package xyz.manzodev.lasttry.Place;

import com.google.android.gms.maps.model.LatLng;

public interface IPlace {
    void onLocationBack(LatLng latLng,String loc);
    void handleMode(int mode);

    void handleSearchData(String data);
    void handleSearchData(LatLng data);
}
