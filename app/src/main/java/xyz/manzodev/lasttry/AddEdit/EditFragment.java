package xyz.manzodev.lasttry.AddEdit;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentEditBinding;


public class EditFragment extends Fragment {
    FragmentEditBinding fragmentEditBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentEditBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit, container, false);
        return fragmentEditBinding.getRoot();
    }

}
