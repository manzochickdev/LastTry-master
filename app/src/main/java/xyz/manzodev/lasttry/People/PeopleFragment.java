package xyz.manzodev.lasttry.People;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.Dump.DumpData;
import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentPeopleBinding;

import static xyz.manzodev.lasttry.Utils.Req.TAG;

public class PeopleFragment extends Fragment {
    FragmentPeopleBinding fragmentPeopleBinding;
    ArrayList<Model> models;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: PeopleFragment");
        context = getContext();
        fragmentPeopleBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_people, container, false);

        getData();
        MainAdapter mainAdapter = new MainAdapter(models,context);
        fragmentPeopleBinding.rvPeople.setAdapter(mainAdapter);
        fragmentPeopleBinding.rvPeople.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        fragmentPeopleBinding.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IMainActivity) context).checkDb();
            }
        });

        fragmentPeopleBinding.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IMainActivity) context).onAddPersonListener();
            }
        });
        return fragmentPeopleBinding.getRoot();
    }

    void getData(){
        models = DumpData.getModelData();
    }

}
