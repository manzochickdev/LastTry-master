package xyz.manzodev.lasttry.Relations;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.RelationsLayoutMainBinding;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<RelationsModel> models;
    Context context;
    RelationsFragment.OnDataListener onDataListener;

    public MainAdapter(ArrayList<RelationsModel> models, Context context, RelationsFragment.OnDataListener onDataListener) {
        this.models = models;
        this.context = context;
        this.onDataListener = onDataListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.relations_layout_main,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SubAdapter subAdapter = new SubAdapter(models.get(i),context,onDataListener);
        viewHolder.relationsLayoutMainBinding.rvPeopleItem.setAdapter(subAdapter);
        viewHolder.relationsLayoutMainBinding.rvPeopleItem.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        viewHolder.relationsLayoutMainBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        RelationsLayoutMainBinding relationsLayoutMainBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relationsLayoutMainBinding = DataBindingUtil.bind(itemView);
        }
    }
}
