package xyz.manzodev.lasttry.Utils.SearchBottom;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.Model.Relation;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.Utils.PersonSearch;
import xyz.manzodev.lasttry.Utils.Relationship;
import xyz.manzodev.lasttry.databinding.FragmentSearchBottomBinding;

public class SearchBottomFragment extends BottomSheetDialogFragment {

    FragmentSearchBottomBinding fragmentSearchBottomBinding;
    ArrayList<Model> models;
    ArrayList<Integer> listId;
    OnDataListener onDataListener;
    Relation relation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSearchBottomBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_bottom, container, false);
        Bundle bundle = this.getArguments();
        int id=-1;
        int relationship = -1;
        if (bundle!=null){
            listId = bundle.getIntegerArrayList("listId");
            id = bundle.getInt("id");
            relationship=bundle.getInt("relationship");

            //todo continue
        }
        relation = new Relation();
        onDataListener = new OnDataListener() {
            @Override
            public void onModelBack(Model model) {
                relation.model = model;
                checkFinish();
            }

            @Override
            public void onRelationshipBack(String relationship) {
                relation.relationship = relationship;
                fragmentSearchBottomBinding.searchBottomRelationshipPicker.setClicked(Relationship.getRelationship().indexOf(relationship));
                checkFinish();
            }
        };
        models = PersonSearch.getInstance(getContext()).distinc(listId).getModels();
        Adapter adapter = new Adapter(models,getContext(),onDataListener);
        fragmentSearchBottomBinding.rvPeople.setAdapter(adapter);
        handleLayoutRelationPicker();
        return fragmentSearchBottomBinding.getRoot();
    }

    private void checkFinish() {
        if (relation.model!=null && relation.relationship!=null) dismiss();
    }

    private void handleLayoutRelationPicker() {
        fragmentSearchBottomBinding.searchBottomRelationshipPicker.setClicked(-1);
        fragmentSearchBottomBinding.searchBottomRelationshipPicker.setOnDataListener(onDataListener);
        fragmentSearchBottomBinding.searchBottomRelationshipPicker.setList(Relationship.getRelationship());
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (relation!=null){
            ((IMainActivity)getContext()).onRelationshipResult(relation);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (relation!=null){
            ((IMainActivity)getContext()).onRelationshipResult(relation);
        }
    }

    public interface OnDataListener{
        void onModelBack(Model model);
        void onRelationshipBack(String relationship);
    }
}
