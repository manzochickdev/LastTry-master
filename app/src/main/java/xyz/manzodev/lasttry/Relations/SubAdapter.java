package xyz.manzodev.lasttry.Relations;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.RelationsLayoutSubBinding;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder>{
    RelationsModel relationsModel;
    Context context;
    RelationsFragment.OnDataListener onDataListener;

    public SubAdapter(RelationsModel relationsModel, Context context, RelationsFragment.OnDataListener onDataListener) {
        this.relationsModel = relationsModel;
        this.context = context;
        this.onDataListener = onDataListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.relations_layout_sub,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Model model = relationsModel.models.get(i);
        viewHolder.relationsLayoutSubBinding.setModel(model);
        viewHolder.relationsLayoutSubBinding.setVisibility(relationsModel.root==model.getId());
        viewHolder.relationsLayoutSubBinding.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (relationsModel.root==model.getId()) ((IMainActivity)context).getSummaryInfo(view,8);
                onDataListener.onDataBack(model.getId(),view);
            }
        });
        viewHolder.relationsLayoutSubBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return relationsModel.models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        RelationsLayoutSubBinding relationsLayoutSubBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relationsLayoutSubBinding = DataBindingUtil.bind(itemView);
        }
    }
}
