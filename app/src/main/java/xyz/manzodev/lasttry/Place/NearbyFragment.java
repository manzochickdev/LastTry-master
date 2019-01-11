package xyz.manzodev.lasttry.Place;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import xyz.manzodev.lasttry.DatabaseHandle;
import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentNearbyBinding;

public class NearbyFragment extends Fragment {
    FragmentNearbyBinding fragmentNearbyBinding;
    Context context;
    int id;
    LatLng latLng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        fragmentNearbyBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_nearby, container, false);

        fragmentNearbyBinding.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IMainActivity)context).onEditPersonListener(id);
            }
        });

        fragmentNearbyBinding.tvDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IPlace) getParentFragment()).getDirection(latLng);
            }
        });
        return fragmentNearbyBinding.getRoot();
    }

    public void showNearby(String title, LatLng position) {
        this.latLng = position;
        fragmentNearbyBinding.tvAddress.setText(title);
        ArrayList<Model> models = DatabaseHandle.getInstance(context).getNearby(position);
        if (models==null) return;
        NearbyAdapter nearbyAdapter = new NearbyAdapter(models,context);
        fragmentNearbyBinding.rvPeople.setAdapter(nearbyAdapter);
    }
}
