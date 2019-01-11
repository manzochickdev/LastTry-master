package xyz.manzodev.lasttry.People;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.PeopleLayoutSubBinding;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {
    ArrayList<Model> models;
    Context context;

    public SubAdapter(ArrayList<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.people_layout_sub,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.peopleLayoutSubBinding.setModel(models.get(i));
        viewHolder.peopleLayoutSubBinding.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IMainActivity) context).onEditPersonListener(models.get(i).getId());
            }
        });
        viewHolder.peopleLayoutSubBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        PeopleLayoutSubBinding peopleLayoutSubBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            peopleLayoutSubBinding = DataBindingUtil.bind(itemView);
        }
    }
}
