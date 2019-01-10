package xyz.manzodev.lasttry.Dump;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.DatabaseHandle;
import xyz.manzodev.lasttry.Model.Address;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentDbdataBinding;


public class DBDataFragment extends Fragment implements View.OnClickListener {
    FragmentDbdataBinding fragmentDbdataBinding;
    DatabaseHandle databaseHandle;
    Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDbdataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_dbdata, container, false);
        databaseHandle = new DatabaseHandle(getContext());
        fragmentDbdataBinding.people.setOnClickListener(this);
        fragmentDbdataBinding.rela.setOnClickListener(this);
        fragmentDbdataBinding.address.setOnClickListener(this);
        fragmentDbdataBinding.detail.setOnClickListener(this);
        return fragmentDbdataBinding.getRoot();
    }

    public void setAdapter(Adapter adapter) {
        fragmentDbdataBinding.rvData.setAdapter(adapter);
        fragmentDbdataBinding.rvData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.people:
                ArrayList<Model> models = databaseHandle.getAllPerson();
                adapter = new Adapter(models,null,null,getContext());
                setAdapter(adapter);
                break;
            case R.id.rela:
                break;
            case R.id.address:
                ArrayList<Address> addresses = databaseHandle.getAllAddress();
                adapter = new Adapter(null,addresses,null,getContext());
                setAdapter(adapter);
                break;
            case R.id.detail:
                break;
        }
    }
}
