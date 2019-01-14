package xyz.manzodev.lasttry.Utils.Search;

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
import xyz.manzodev.lasttry.Utils.PersonSearch;
import xyz.manzodev.lasttry.databinding.PersonSearchLayoutItemBinding;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<Model> models;
    Context context;
    int clicked=-1;
    PersonSearchFragment.OnItemClickListener onItemClickListener;

    public Adapter(ArrayList<Model> models, Context context, PersonSearchFragment.OnItemClickListener onItemClickListener) {
        this.models = models;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_search_layout_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Model model = models.get(i);
        viewHolder.personSearchLayoutItemBinding.setModel(model);
        viewHolder.personSearchLayoutItemBinding.setVisibility(clicked==model.getId());
        viewHolder.personSearchLayoutItemBinding.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(model);
                clicked=model.getId();
                notifyDataSetChanged();
            }
        });
        viewHolder.personSearchLayoutItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        PersonSearchLayoutItemBinding personSearchLayoutItemBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            personSearchLayoutItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
