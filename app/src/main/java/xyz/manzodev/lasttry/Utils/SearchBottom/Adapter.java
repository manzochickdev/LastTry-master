package xyz.manzodev.lasttry.Utils.SearchBottom;

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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<Model> models;
    Context context;
    SearchBottomFragment.OnDataListener onDataListener;

    public Adapter(ArrayList<Model> models, Context context, SearchBottomFragment.OnDataListener onDataListener) {
        this.models = models;
        this.context = context;
        this.onDataListener = onDataListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_bottom_layout_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.searchBottomLayoutItemBinding.setModel(models.get(i));
        viewHolder.searchBottomLayoutItemBinding.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDataListener.onModelBack(models.get(i));
            }
        });
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
