package xyz.manzodev.lasttry.Place;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.SearchBottomLayoutItemBinding;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {
    ArrayList<Model> models;
    Context context;

    public NearbyAdapter(ArrayList<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_bottom_layout_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.searchBottomLayoutItemBinding.setModel(models.get(i));
        viewHolder.searchBottomLayoutItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        SearchBottomLayoutItemBinding searchBottomLayoutItemBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchBottomLayoutItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
