package xyz.manzodev.lasttry.Utils.Search;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.Utils.PersonSearch;
import xyz.manzodev.lasttry.databinding.FragmentPersonSearchBinding;


public class PersonSearchFragment extends Fragment {
    FragmentPersonSearchBinding fragmentPersonSearchBinding;
    Context context;
    String targetFragment;
    OnItemClickListener onItemClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        fragmentPersonSearchBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_person_search, container, false);
        Bundle bundle = this.getArguments();
        targetFragment = bundle.getString("target");

        onItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(Model model) {
                ((IMainActivity)context).onPersonSearchFinish(model,targetFragment);
            }
        };

        ArrayList<Model> models = PersonSearch.getInstance(context).getModels();
        Adapter adapter = new Adapter(models,context,onItemClickListener);
        fragmentPersonSearchBinding.rvPeople.setAdapter(adapter);
        fragmentPersonSearchBinding.rvPeople.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        return fragmentPersonSearchBinding.getRoot();
    }

    public interface OnItemClickListener{
        void onItemClick(Model model);
    }

}
