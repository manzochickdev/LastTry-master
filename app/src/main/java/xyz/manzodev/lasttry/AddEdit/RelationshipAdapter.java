package xyz.manzodev.lasttry.AddEdit;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.Model.Relation;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.AddeditLayoutRelationshipAddBinding;
import xyz.manzodev.lasttry.databinding.AddeditLayoutRelationshipItemBinding;

public class RelationshipAdapter extends RecyclerView.Adapter<RelationshipAdapter.ViewHolder> {
    ArrayList<Relation> relations;
    RelationshipVM.OnDataListener onDataListener;
    Context context;

    public RelationshipAdapter(ArrayList<Relation> relations, RelationshipVM.OnDataListener onDataListener, Context context) {
        this.relations = relations;
        this.onDataListener = onDataListener;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (i){
            case 0:
                AddeditLayoutRelationshipItemBinding item = DataBindingUtil
                        .inflate(inflater, R.layout.addedit_layout_relationship_item,viewGroup,false);
                return new ViewHolder(item);
            case 1:
                AddeditLayoutRelationshipAddBinding add = DataBindingUtil
                        .inflate(inflater,R.layout.addedit_layout_relationship_add,viewGroup,false);
                return new ViewHolder(add);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        switch (viewHolder.getItemViewType()){
            case 0:
                viewHolder.item.setRela(relations.get(i));
                viewHolder.item.ivRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDataListener.onRemoveRequest(relations.get(i));
                    }
                });
                viewHolder.item.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDataListener.onRelationshipPicker(relations.get(i));
                    }
                });
                viewHolder.item.executePendingBindings();
                break;
            case 1:
                viewHolder.add.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDataListener.onAddRequest();
                    }
                });
                viewHolder.add.executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return relations.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position<=relations.size()-1){
            return 0;
        }
        else return 1;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        AddeditLayoutRelationshipItemBinding item;
        AddeditLayoutRelationshipAddBinding add;

        public ViewHolder(@NonNull AddeditLayoutRelationshipItemBinding item) {
            super(item.getRoot());
            this.item = item;
        }

        public ViewHolder(@NonNull AddeditLayoutRelationshipAddBinding add) {
            super(add.getRoot());
            this.add = add;
        }
    }
}
