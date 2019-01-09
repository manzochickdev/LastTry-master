package xyz.manzodev.lasttry.Place.MapUtils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FetchAddress extends AsyncTask<String,Void,LatLng> {
    Context mContext;

    public FetchAddress(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected LatLng doInBackground(String... strings) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        LatLng latLng = null;
        try {
            List<Address> addresses = geocoder.getFromLocationName(strings[0],1);
            double lat = addresses.get(0).getLatitude();
            double lng = addresses.get(0).getLongitude();
            latLng = new LatLng(lat,lng);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return latLng;
    }

    @Override
    protected void onPostExecute(LatLng latLng) {
        super.onPostExecute(latLng);
    }
}
