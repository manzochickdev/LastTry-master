package xyz.manzodev.lasttry.Place;


import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import xyz.manzodev.lasttry.BR;
import xyz.manzodev.lasttry.Model.Address;
import xyz.manzodev.lasttry.Place.MapUtils.PeopleSearchAdapter;
import xyz.manzodev.lasttry.Place.MapUtils.PlaceAutocompleteAdapter;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentMapHandleBinding;
import xyz.manzodev.lasttry.databinding.MapLayoutPeopleSearchAutocompleteBinding;

public class MapHandleFragment extends Fragment {
    FragmentMapHandleBinding fragmentMapHandleBinding;
    IPlace iPlace;
    public Address address;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        iPlace = (IPlace) getParentFragment();
        context = getContext();
        fragmentMapHandleBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_map_handle, container, false);
        fragmentMapHandleBinding.setVh(new ViewHandle());
        return fragmentMapHandleBinding.getRoot();
    }

    public void onLocationBack(LatLng latLng, String loc) {
        address = new Address(loc,latLng);
        if (fragmentMapHandleBinding.tvPlace.getVisibility()==View.VISIBLE) fragmentMapHandleBinding.tvPlace.setText(loc);
    }

    void setAdapter(int mode){
        fragmentMapHandleBinding.etPlace.setDropDownVerticalOffset(20);
        fragmentMapHandleBinding.etPlace.setText(null);
        if (mode==0){
            GeoDataClient geoDataClient = Places.getGeoDataClient(context, null);
            PlaceAutocompleteAdapter placeAutocompleteAdapter = new PlaceAutocompleteAdapter(context,geoDataClient,null);
            fragmentMapHandleBinding.etPlace.setAdapter(placeAutocompleteAdapter);
            fragmentMapHandleBinding.etPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String data = fragmentMapHandleBinding.etPlace.getText().toString();
                    iPlace.handleSearchData(data);
                }
            });
        }
        else{
            PeopleSearchAdapter peopleSearchAdapter = new PeopleSearchAdapter(context);
            fragmentMapHandleBinding.etPlace.setAdapter(peopleSearchAdapter);
            fragmentMapHandleBinding.etPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MapLayoutPeopleSearchAutocompleteBinding layout = DataBindingUtil.bind(view);
                    Address address = layout.getAddress();
                    if (address!=null) iPlace.handleSearchData(address);
                }
            });
        }
    }


    public class ViewHandle extends BaseObservable{
        int mode=2;
        boolean isPlaceSearch;


        public void onModeChange(int mode){
            this.mode = mode;
            notifyPropertyChanged(BR.mode);
            notifyPropertyChanged(BR.placeSearch);

            if (mode==0 || mode==1){
                setAdapter(mode);
            }

            iPlace.handleMode(mode);
        }

        @Bindable
        public int getMode() {
            return mode;
        }

        @Bindable
        public boolean isPlaceSearch() {
            if (mode==0 || mode==1) return true;
            else return false;
        }
        
    }

}
