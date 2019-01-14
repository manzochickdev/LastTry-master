package xyz.manzodev.lasttry.Place.MapUtils;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    Context context;
    String address;
    ArrayList<Integer> listId;
    View mView;

    public CustomInfoWindow(Context context, String address, ArrayList<Integer> listId) {
        this.context = context;
        this.address = address;
        this.listId = listId;

    }

    private void onRenderLayout() {
    }

    @Override
    public View getInfoWindow(Marker marker) {
        onRenderLayout();
        return mView;
    }


    @Override
    public View getInfoContents(Marker marker) {
        onRenderLayout();
        return mView;
    }
}
