package xyz.manzodev.lasttry.AddEdit;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import xyz.manzodev.lasttry.BR;
import xyz.manzodev.lasttry.DatabaseHandle;
import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.Model.Relation;
import xyz.manzodev.lasttry.Utils.Relationship;

public class RelationshipVM extends BaseObservable {
    boolean isEdit;
    RelationshipAdapter relationshipAdapter;
    int id;
    ArrayList<Relation> relations;
    Context context;
    public OnDataListener onDataListener = new OnDataListener() {
        @Override
        public void onAddRequest() {
            onAddRelationship();
        }

        @Override
        public void onRemoveRequest(Relation r) {
            onDeleteRelationship(r);
        }

        Relation holder;
        @Override
        public void onRelationshipPicker(Relation relation) {
            ArrayList<Integer> listId = new ArrayList<>();
            for (Relation r : relations){
                if (r.model!=null) listId.add(r.model.id);
            }
            holder = relation;
            ((IMainActivity)context).getRelationshipPicker(this,listId,relation);
        }

        @Override
        public void onRelationshipBack(Relation relation) {
            if (relation.model!=null) holder.model = relation.model;
            if (relation.relationship!=null) holder.relationship = relation.relationship;
            notifyPropertyChanged(BR.relations);
            relationshipAdapter.notifyDataSetChanged();
        }

    };

    public RelationshipVM(int id, Context context) {
        this.id = id;
        this.context = context;
        this.relations = DatabaseHandle.getInstance(context).getAllRelation(id);
        if (this.relations==null) this.relations = new ArrayList<>();
        isEdit = false;
        this.relationshipAdapter = new RelationshipAdapter(relations,onDataListener,context,isEdit);
    }

    public RelationshipVM(Context context) {
        this.id = -1;
        this.context = context;
        this.relations = new ArrayList<>();
        isEdit = true;
        this.relationshipAdapter = new RelationshipAdapter(relations,onDataListener,context,isEdit);
    }

    public void onAddRelationship(){
        relations.add(new Relation());
        notifyPropertyChanged(BR.relations);
        relationshipAdapter.notifyDataSetChanged();
    }

    public void onDeleteRelationship(Relation r){
        //relations.remove(pos);
        relations.remove(r);
        notifyPropertyChanged(BR.relations);
        relationshipAdapter.notifyDataSetChanged();
    }

    @Bindable
    public ArrayList<Relation> getRelations() {
        return relations;
    }

    @Bindable
    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyPropertyChanged(BR.edit);
        if (isEdit==true){
            relationshipAdapter.setIsEdit();
        }
    }

    public ArrayList<Relation> getRelationshipData(){
        ArrayList<Relation> data = new ArrayList<>();
        for (Relation r : relations){
            if (r.model!=null && r.relationship!=null) data.add(r);
        }
        return data;
    }

    public RelationshipAdapter getRelationshipAdapter() {
        return relationshipAdapter;
    }


    public interface OnDataListener{
        void onAddRequest();
        void onRemoveRequest(Relation r);
        void onRelationshipPicker(Relation relation);
        void onRelationshipBack(Relation relation);
    }
}
