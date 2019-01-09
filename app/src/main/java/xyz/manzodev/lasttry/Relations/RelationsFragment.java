package xyz.manzodev.lasttry.Relations;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentRelationsBinding;

import static xyz.manzodev.lasttry.Utils.Req.TAG;


public class RelationsFragment extends Fragment {
    FragmentRelationsBinding fragmentRelationsBinding;
    Context context;
    int id;
    ArrayList<RelationsModel> relationsModels;
    MainAdapter mainAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: RelationsFragment");
        fragmentRelationsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_relations, container, false);
        context = getContext();

        Bundle bundle = this.getArguments();
        if(bundle!=null){
            this.id = bundle.getInt("id");
            init(id);
        }

        fragmentRelationsBinding.ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IMainActivity)getContext()).onPersonSearchListener(RelationsFragment.class.getSimpleName());
            }
        });
        return fragmentRelationsBinding.getRoot();
    }

    private void init(int id) {
        //Model model = databaseHandle.getPerson(id);
        Model model = new Model();
        ArrayList<Model> models = new ArrayList<>();
        models.add(model);
        relationsModels = new ArrayList<>();
        relationsModels.add(new RelationsModel(models));
        mainAdapter = new MainAdapter(relationsModels,context);
        fragmentRelationsBinding.rvPeople.setAdapter(mainAdapter);
        fragmentRelationsBinding.rvPeople.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }

    public void onModelBack(Model model) {
        Log.d(TAG, "onModelBack: "+model.name);
    }
}
