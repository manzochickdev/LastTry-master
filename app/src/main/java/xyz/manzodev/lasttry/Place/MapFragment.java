package xyz.manzodev.lasttry.Place;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import xyz.manzodev.lasttry.DatabaseHandle;
import xyz.manzodev.lasttry.Place.MapUtils.FetchAddress;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentMapBinding;

import static xyz.manzodev.lasttry.Utils.Req.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static xyz.manzodev.lasttry.Utils.Req.PERMISSIONS_REQUEST_ENABLE_GPS;
import static xyz.manzodev.lasttry.Utils.Req.PLACE_PICKER;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    FragmentMapBinding fragmentMapBinding;
    GoogleMap googleMap;
    Context context;
    LatLng currentLocation;
    IPlace iPlace;
    String mode="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        iPlace = (IPlace) getParentFragment();
        fragmentMapBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_map, container, false);
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            mode = bundle.getString("mode");
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return fragmentMapBinding.getRoot();
    }

    private void getAllNearby() {
        ArrayList<xyz.manzodev.lasttry.Model.Address> addresses = DatabaseHandle.getInstance(context).getAllAddressDistinc();
        for (xyz.manzodev.lasttry.Model.Address a : addresses){
            googleMap.addMarker(new MarkerOptions().position(a.latlng).title(a.getTextAddr()));
        }
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                ((IPlace)getParentFragment()).showNearby(marker.getTitle(),marker.getPosition());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        getCurrentLocation();
        GoogleMap.OnCameraMoveListener o = null;
        if (!mode.equals(PLACE_PICKER)){
            getAllNearby();
            o = new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {
                    ((IPlace) getParentFragment()).hideNearby();
                }
            };
        }
        googleMap.setOnCameraMoveListener(o);
    }

    public void moveCamera(LatLng location, String address) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        if (mode.equals(PLACE_PICKER)) googleMap.addMarker(new MarkerOptions().position(location).title(address));
    }

    public void moveCamera(String data) {
        FetchAddress fetchAddress = new FetchAddress(context);
        try {
            LatLng latLng = fetchAddress.execute(data).get();
            iPlace.onLocationBack(latLng,data);
            moveCamera(latLng,data);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getCurrentLocation(){
        if (handleServiceandPermission())
        {
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
    }

    public void getDirection(LatLng latLng) {
        if  (currentLocation!=null){
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps/dir/?api=1&origin="+currentLocation.latitude+","+currentLocation.longitude+"&destination="+latLng.latitude+","+latLng.longitude));
            startActivity(intent);
        }
        else Toast.makeText(context, "Get Location Error", Toast.LENGTH_SHORT).show();
    }


    public void getPinnedLocation() {
        if (mode.equals(PLACE_PICKER)) googleMap.clear();
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

    boolean mLocationPermissionGranted;
    private boolean handleServiceandPermission(){
        //gps
        boolean isGpsEnabled;
        LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            isGpsEnabled = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("This application need GPS to work correctly , do you want to enable it ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent,PERMISSIONS_REQUEST_ENABLE_GPS);
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else isGpsEnabled = true;

        //permission
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            mLocationPermissionGranted = false;
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (isGpsEnabled && mLocationPermissionGranted) return true;
        return false;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
                else {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                        intent.setData(uri);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(intent, 993);
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PERMISSIONS_REQUEST_ENABLE_GPS:{
                getCurrentLocation();
            }
            break;
        }
    }

}
