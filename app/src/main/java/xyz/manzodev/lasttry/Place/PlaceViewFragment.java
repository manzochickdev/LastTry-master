package xyz.manzodev.lasttry.Place;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.model.LatLng;

import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Address;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.Utils.Req;
import xyz.manzodev.lasttry.databinding.FragmentPlaceViewBinding;

public class PlaceViewFragment extends Fragment implements IPlace {
    FragmentPlaceViewBinding fragmentPlaceViewBinding;
    MapFragment mapFragment;
    MapHandleFragment mapHandleFragment;
    String mode="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPlaceViewBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_place_view, container, false);
        Bundle bundle = this.getArguments();
        if (bundle!=null) mode = bundle.getString("mode");
        if (mode.equals(Req.PLACE_PICKER)){
            fragmentPlaceViewBinding.setMode(true);
        }
        else {
            fragmentPlaceViewBinding.setMode(false);
            fragmentPlaceViewBinding.bottomHolder.setVisibility(View.GONE);
            NearbyFragment nearbyFragment = new NearbyFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.bottom_holder,nearbyFragment,NearbyFragment.class.getSimpleName()).commit();
        }

        mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.map_holder,mapFragment).commit();
        mapHandleFragment = (MapHandleFragment) getChildFragmentManager().findFragmentById(R.id.map_handle_fragment);

        fragmentPlaceViewBinding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address = mapHandleFragment.address;
                if (address!=null) ((IMainActivity) getContext()).onPlacePickerResult(address);
            }
        });
        return fragmentPlaceViewBinding.getRoot();
    }


    @Override
    public void onLocationBack(LatLng latLng, String loc) {
        mapHandleFragment.onLocationBack(latLng,loc);
    }

    @Override
    public void handleMode(int mode) {
        mapFragment.handleMode(mode);
    }

    @Override
    public void handleSearchData(String data) {
        mapFragment.moveCamera(data);
    }

    @Override
    public void handleSearchData(Address data) {
        mapFragment.moveCamera(data.latlng,data.getTextAddr());
        onLocationBack(data.latlng,data.getTextAddr());
    }

    @Override
    public void showNearby(String title, LatLng position) {
        fragmentPlaceViewBinding.bottomHolder.setVisibility(View.VISIBLE);
        NearbyFragment nearbyFragment = (NearbyFragment) getChildFragmentManager().findFragmentByTag(NearbyFragment.class.getSimpleName());
        nearbyFragment.showNearby(title,position);

    }

    @Override
    public void hideNearby() {
        fragmentPlaceViewBinding.bottomHolder.setVisibility(View.GONE);
    }

    @Override
    public void getDirection(LatLng latLng) {
        mapFragment.getDirection(latLng);
    }

}
