package xyz.manzodev.lasttry.Utils.SearchBottom;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentSearchBottomBinding;

public class SearchBottomFragment extends BottomSheetDialogFragment {

    FragmentSearchBottomBinding fragmentSearchBottomBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSearchBottomBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_bottom, container, false);
        return fragmentSearchBottomBinding.getRoot();
    }
}
