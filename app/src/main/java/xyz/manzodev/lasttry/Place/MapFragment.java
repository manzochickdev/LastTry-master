package xyz.manzodev.lasttry.Place;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import xyz.manzodev.lasttry.Place.MapUtils.FetchAddress;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentMapBinding;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    FragmentMapBinding fragmentMapBinding;
    GoogleMap googleMap;
    Context context;
    LatLng currentLocation;
    IPlace iPlace;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        iPlace = (IPlace) getParentFragment();
        fragmentMapBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return fragmentMapBinding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        getCurrentLocation();
    }

    public void moveCamera(LatLng location, String address) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }

    public void moveCamera(String data) {
        FetchAddress fetchAddress = new FetchAddress(context);
        try {
            LatLng latLng = fetchAddress.execute(data).get();
            iPlace.onLocationBack(latLng,data);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getCurrentLocation(){
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    String data="";
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        if (addresses.size()!=0){
                            data = addresses.get(0).getAddressLine(0);
                        }
                        else data = currentLocation.latitude+","+currentLocation.longitude;
                    } catch (IOException e) {
                        data = currentLocation.latitude+","+currentLocation.longitude;
                        e.printStackTrace();
                    }
                    finally {
                        moveCamera(currentLocation, data);
                        iPlace.onLocationBack(currentLocation,data);
                    }
                }
                else Toast.makeText(context, "Get Location Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getPinnedLocation() {
        fragmentMapBinding.ivPin.setVisibility(View.VISIBLE);
        final Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                int x = fragmentMapBinding.getRoot().getWidth() / 2;
                int y = fragmentMapBinding.getRoot().getHeight() / 2;
                LatLng position = googleMap.getProjection().fromScreenLocation(new Point(x, y));
                String s = "";
                try {
                    List<Address> addresses = geocoder.getFromLocation(position.latitude,position.longitude,1);
                    if (addresses.size()!=0){
                        s = addresses.get(0).getAddressLine(0);
                    }
                    else {
                        s = position.latitude+","+position.longitude;
                    }

                } catch (IOException e) {
                    s = position.latitude+","+position.longitude;
                    e.printStackTrace();
                }
                finally {
                    iPlace.onLocationBack(position,s);
                }
            }
        });
    }

    public void handleMode(int mode) {
        fragmentMapBinding.ivPin.setVisibility(View.GONE);
        googleMap.setOnCameraIdleListener(null);
        switch (mode){
            case 0: break;
            case 1: break;
            case 2:
                getCurrentLocation();
                break;
            case 3:
                getPinnedLocation();
                break;
        }
    }

}
