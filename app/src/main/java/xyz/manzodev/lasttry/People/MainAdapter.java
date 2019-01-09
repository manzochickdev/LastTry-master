package xyz.manzodev.lasttry.People;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.PeopleLayoutMainBinding;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<ArrayList<Model>> models;
    Context context;

    public MainAdapter(ArrayList<Model> models, Context context) {
        this.models = handleList(models);
        this.context = context;
    }

    private ArrayList<ArrayList<Model>> handleList(ArrayList<Model> models) {
        ArrayList<ArrayList<Model>> lists = new ArrayList<>();
        while(models.size()>=3){
            ArrayList<Model> temp = new ArrayList<>(models.subList(0,3));
            lists.add(temp);
            models.removeAll(temp);
        }
        lists.add(models);
        return lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.people_layout_main,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SubAdapter subAdapter = new SubAdapter(models.get(i),context);
        viewHolder.peopleLayoutMainBinding.rvPeopleItem.setAdapter(subAdapter);
        viewHolder.peopleLayoutMainBinding.rvPeopleItem.setLayoutManager(new GridLayoutManager(context,3, LinearLayoutManager.VERTICAL,false));
        viewHolder.peopleLayoutMainBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        PeopleLayoutMainBinding peopleLayoutMainBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            peopleLayoutMainBinding = DataBindingUtil.bind(itemView);
        }
    }
}
