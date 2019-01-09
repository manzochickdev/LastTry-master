package xyz.manzodev.lasttry.AddEdit;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentAddBinding;


public class AddFragment extends Fragment {
    FragmentAddBinding fragmentAddBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAddBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add, container, false);
        fragmentAddBinding.addeditLayoutRelationship.setVm(new RelationshipVM(getContext()));
        return fragmentAddBinding.getRoot();
    }

}
