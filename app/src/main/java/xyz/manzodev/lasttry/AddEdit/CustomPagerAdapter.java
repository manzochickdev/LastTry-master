package xyz.manzodev.lasttry.AddEdit;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.manzodev.lasttry.DatabaseHandle;
import xyz.manzodev.lasttry.Model.Address;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.Model.Relation;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.AddeditLayoutInfoBinding;
import xyz.manzodev.lasttry.databinding.AddeditLayoutRelationshipBinding;
import xyz.manzodev.lasttry.databinding.AddeditLayoutRelationshipAddBinding;

public class CustomPagerAdapter extends PagerAdapter {
    int id;
    Context context;
    AddeditLayoutInfoBinding addeditLayoutInfoBinding;
    AddeditLayoutRelationshipBinding addeditLayoutRelationshipBinding;


    public CustomPagerAdapter(int id, Context context) {
        this.id = id;
        this.context = context;
        databaseHandle = DatabaseHandle.getInstance(context);
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (position){
            case 0 :
                View layoutInfo = inflater.inflate(R.layout.addedit_layout_info,null,false);
                handleLayoutInfo(layoutInfo);
                container.addView(layoutInfo);
                return layoutInfo;
            case 1 :
                View layoutRelationship = inflater.inflate(R.layout.addedit_layout_relationship,null,false);
                handleLayoutRelationship(layoutRelationship);
                container.addView(layoutRelationship);
                return layoutRelationship;
        }
        return null;
    }

    DatabaseHandle databaseHandle;
    private void handleLayoutInfo(View layoutInfo) {
        addeditLayoutInfoBinding = DataBindingUtil.bind(layoutInfo);

        addeditLayoutInfoBinding.tvTitle.setVisibility(View.GONE);
        addeditLayoutInfoBinding.viewHolder.setVisibility(View.GONE);


        Model model = databaseHandle.getPerson(id);
        Address address = databaseHandle.getAddress(id);
        addeditLayoutInfoBinding.setVm(new InfoVM(model,address,context));
    }

    private void handleLayoutRelationship(View layoutRelationship) {
        addeditLayoutRelationshipBinding = DataBindingUtil.bind(layoutRelationship);

        addeditLayoutRelationshipBinding.tvTitle.setVisibility(View.GONE);
        addeditLayoutRelationshipBinding.viewHolder.setVisibility(View.GONE);

        RelationshipVM relationshipVM = new RelationshipVM(id,context);
        relationshipVM.getRelationshipData();
        addeditLayoutRelationshipBinding.setVm(relationshipVM);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0) return "Info";
        return "Relationship";
    }

    public void setIsEdit(){
        addeditLayoutInfoBinding.getVm().setEdit(true);
        addeditLayoutRelationshipBinding.getVm().setEdit(true);
    }

    public void handleUpdate(){
        InfoVM infoVM = addeditLayoutInfoBinding.getVm();
        ArrayList<Relation> relations = addeditLayoutRelationshipBinding.getVm().getRelationshipData();

        Model model = infoVM.getModel();
        databaseHandle.updatePerson(model);

        Address address = infoVM.getAddress();
        //todo continue
    }


}
