package xyz.manzodev.lasttry.Place;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPlaceViewBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_place_view, container, false);
        Bundle bundle = this.getArguments();
        String mode = bundle.getString("mode");
        if (mode.equals(Req.PLACE_PICKER)){
            fragmentPlaceViewBinding.setMode(true);
        }
        else fragmentPlaceViewBinding.setMode(false);

        mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
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
    public void handleSearchData(LatLng data) {
        mapFragment.moveCamera(data,null);
    }

}
