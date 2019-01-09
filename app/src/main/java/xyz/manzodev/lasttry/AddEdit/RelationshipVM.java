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

public class RelationshipVM extends BaseObservable {
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
    }

    public RelationshipVM(Context context) {
        this.id = -1;
        this.context = context;
        this.relations = new ArrayList<>();
    }

    public void onAddRelationship(){
        relations.add(new Relation());
        notifyPropertyChanged(BR.relations);
        notifyPropertyChanged(BR.vm);
    }

    public void onDeleteRelationship(int pos){
        relations.remove(pos);
        notifyPropertyChanged(BR.relations);
    }

    public void getRelationshipPicker(){
        ((IMainActivity) context).getRelationshipPicker();
    }

    @Bindable
    public ArrayList<Relation> getRelations() {
        return relations;
    }

    @BindingAdapter("setRelationshipAdapter")
    public static void setRelationshipAdapter(RecyclerView view, RelationshipVM vm){
        RelationshipAdapter relationshipAdapter = new RelationshipAdapter(vm.getRelations(),vm.onDataListener,view.getContext());
        view.setAdapter(relationshipAdapter);
        view.setLayoutManager(new GridLayoutManager(view.getContext(),3,LinearLayoutManager.VERTICAL,false));
    }

    public interface OnDataListener{
        void onAddRequest();
    }
}
