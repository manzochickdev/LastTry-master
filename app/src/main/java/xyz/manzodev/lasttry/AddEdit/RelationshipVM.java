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
import xyz.manzodev.lasttry.Model.Relation;
import xyz.manzodev.lasttry.Utils.Relationship;

public class RelationshipVM extends BaseObservable {
    RelationshipAdapter relationshipAdapter;
    int id;
    ArrayList<Relation> relations;
    Context context;
    public OnDataListener onDataListener = new OnDataListener() {
        @Override
        public void onAddRequest() {
            onAddRelationship();
        }
    };

    public RelationshipVM(int id, Context context) {
        this.id = id;
        this.context = context;
        this.relations = DatabaseHandle.getInstance(context).getAllRelation(id);
        this.relationshipAdapter = new RelationshipAdapter(relations,onDataListener,context);
    }

    public RelationshipVM(Context context) {
        this.id = -1;
        this.context = context;
        this.relations = new ArrayList<>();
        this.relationshipAdapter = new RelationshipAdapter(relations,onDataListener,context);
    }

    public void onAddRelationship(){
        relations.add(new Relation());
        notifyPropertyChanged(BR.relations);
        relationshipAdapter.notifyDataSetChanged();
    }

    public void onDeleteRelationship(int pos){
        relations.remove(pos);
        notifyPropertyChanged(BR.relations);
        relationshipAdapter.notifyDataSetChanged();
    }

    public void getRelationshipPicker(){
        ((IMainActivity) context).getRelationshipPicker();
    }

    @Bindable
    public ArrayList<Relation> getRelations() {
        return relations;
    }

    public RelationshipAdapter getRelationshipAdapter() {
        return relationshipAdapter;
    }


    public interface OnDataListener{
        void onAddRequest();
    }
}
