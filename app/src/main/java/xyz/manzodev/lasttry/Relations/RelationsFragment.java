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

import xyz.manzodev.lasttry.DatabaseHandle;
import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.Utils.Relationship;
import xyz.manzodev.lasttry.databinding.FragmentRelationsBinding;

import static xyz.manzodev.lasttry.Utils.Req.TAG;


public class RelationsFragment extends Fragment {
    FragmentRelationsBinding fragmentRelationsBinding;
    Context context;
    int id;
    ArrayList<RelationsModel> relationsModels;
    MainAdapter mainAdapter;
    OnDataListener onDataListener;
    DatabaseHandle databaseHandle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: RelationsFragment");
        fragmentRelationsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_relations, container, false);
        context = getContext();
        databaseHandle = DatabaseHandle.getInstance(context);

        onDataListener = new OnDataListener() {

            @Override
            public void onDataBack(int id) {
                getRelation(id);
            }
        };

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
        Model model = databaseHandle.getPerson(id);
        ArrayList<Model> models = new ArrayList<>();
        models.add(model);
        relationsModels = new ArrayList<>();
        relationsModels.add(new RelationsModel(models));
        mainAdapter = new MainAdapter(relationsModels,context,onDataListener);
        fragmentRelationsBinding.rvPeople.setAdapter(mainAdapter);
        fragmentRelationsBinding.rvPeople.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }

    public void onModelBack(Model model) {
        init(model.getId());
    }

    private void getRelation(int id) {
        int pos = getPosition(id)[1];

        if (relationsModels.get(pos).root==id){
            return;
        }
        updateParent(id);
        updateChild(id);
        updateSibling(id);

        pos = getPosition(id)[1];
        relationsModels.get(pos).root = id;
    }

    private void updateParent(int id) {
        int pos = getPosition(id)[1];
        ArrayList<Model> parent = databaseHandle.getRelation(id, Relationship.PARENT);
        if (parent!=null){
            try{
                RelationsModel p = relationsModels.get(pos-1);
                if (!isContain(relationsModels.get(pos-1).models,parent)){
                    relationsModels.set(pos-1,new RelationsModel(parent));
                }
            }
            catch (Exception e){
                relationsModels.add(0,new RelationsModel(parent));
            }
        }
        notifyDataChange();
    }


    private void updateChild(int id) {
        int pos = getPosition(id)[1]+1;
        ArrayList<Model> children = databaseHandle.getRelation(id,Relationship.CHILD);
        if (children!=null){
            try {
                RelationsModel p1 = relationsModels.get(pos);
                if (!isContain(relationsModels.get(pos).models,children)){
                    while(pos<relationsModels.size()){
                        relationsModels.remove(pos);
                    }
                    relationsModels.add(new RelationsModel(children));
                }
            }
            catch (Exception e){
                relationsModels.add(new RelationsModel(children));
            }

        }
        else {
            while(pos<relationsModels.size()){
                relationsModels.remove(pos);
            }
        }
        notifyDataChange();
    }

    private void updateSibling(int id) {
        int pos = getPosition(id)[1];
        ArrayList<Model> siblings = new ArrayList<>();
        ArrayList<Model> parent = databaseHandle.getRelation(id,Relationship.PARENT);
        ArrayList<Model> s = databaseHandle.getRelation(id,Relationship.SIBLING);
        if (parent!=null){
            siblings.addAll(databaseHandle.getRelation(parent.get(0).getId(),Relationship.CHILD));
        }
        if (s!=null){
            siblings.addAll(s);
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0;i<siblings.size();i++){
            for (int n=0;n<relationsModels.get(pos).models.size();n++){
                if (siblings.get(i).getId()==relationsModels.get(pos).models.get(n).getId()){
                    list.add(i);
                }
            }
        }

        for (int i=list.size()-1;i>=0;i--){
            siblings.remove(list.get(i));
        }

        for (Model m : siblings){
            boolean check=false;
            for (Model n : relationsModels.get(pos).models){
                if (m.getId()==n.getId()){
                    check=true;
                }
            }
            if (!check){
                relationsModels.get(pos).models.add(m);
            }
        }

        notifyDataChange();
    }

    private boolean isContain(ArrayList<Model> m1, ArrayList<Model> m2) {
        if (m2.size()>m1.size())return false;
        boolean[] check = new boolean[m2.size()];
        for (Model m :m2){
            for (Model n : m1){
                if (m.getId() == n.getId()){
                    check[m2.indexOf(m)]=true;
                }
            }
        }
        for (int i=0;i<check.length;i++){
            if (!check[i]) return false;
        }
        return true;
    }

    private void notifyDataChange() {
        mainAdapter.notifyDataSetChanged();
    }

    private int[] getPosition(int id){
        int[] temp = new int[2];
        for (RelationsModel list : relationsModels){
            for (Model m : list.models){
                if (m.getId() == id){
                    //column
                    temp[0] = list.models.indexOf(m);
                    //row
                    temp[1] = relationsModels.indexOf(list);
                }
            }
        }
        return temp;
    }

    public interface OnDataListener{
        void onDataBack(int id);
    }
}
