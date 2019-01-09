package xyz.manzodev.lasttry.Dump;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.Model.Address;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.Model.Relation;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.DumpDataLayoutBinding;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<Model> models;
    ArrayList<Address> addresses;
    ArrayList<Relation> relations;
    Context context;

    public Adapter(ArrayList<Model> models, ArrayList<Address> addresses, ArrayList<Relation> relations, Context context) {
        this.models = models;
        this.addresses = addresses;
        this.relations = relations;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dump_data_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (models!=null){
            Model model = models.get(i);
            viewHolder.dumpDataLayoutBinding.tvData.setText(model.name+" - "+model.dispRela);
        }
        else if (addresses!=null){
            Address address = addresses.get(i);
            viewHolder.dumpDataLayoutBinding.tvData.setText(address.getTextAddr());
        }
        else if (relations!=null){
            Relation relation = relations.get(i);
            viewHolder.dumpDataLayoutBinding.tvData.setText(relation.relationship+" - "+relation.model.name);
        }
        viewHolder.dumpDataLayoutBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (models!=null) return models.size();
        else if (addresses!=null) return addresses.size();
        else if (relations!=null) return relations.size();
        return 0;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        DumpDataLayoutBinding dumpDataLayoutBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dumpDataLayoutBinding = DataBindingUtil.bind(itemView);

        }
    }
}
