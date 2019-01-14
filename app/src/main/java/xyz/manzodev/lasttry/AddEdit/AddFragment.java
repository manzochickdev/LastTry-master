package xyz.manzodev.lasttry.AddEdit;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Relation;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentAddBinding;


public class AddFragment extends Fragment {
    FragmentAddBinding fragmentAddBinding;
    Context context;
    boolean isImageProfileChange=false;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        fragmentAddBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add, container, false);
        fragmentAddBinding.addeditLayoutInfo.setVm(new InfoVM(context));
        fragmentAddBinding.addeditLayoutRelationship.setVm(new RelationshipVM(context));

        fragmentAddBinding.civProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IMainActivity)context).getImagePicker(AddFragment.class.getSimpleName());
            }
        });

        fragmentAddBinding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSave();
            }
        });
        return fragmentAddBinding.getRoot();
    }

    private void handleSave() {
        InfoVM infoVM = fragmentAddBinding.addeditLayoutInfo.getVm();
        ArrayList<Relation> relations = fragmentAddBinding.addeditLayoutRelationship.getVm().getRelationshipData();
        if (infoVM.model.name==null || infoVM.model.name.trim().length()==0){
            Toast.makeText(context, "Name can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isImageProfileChange){
            Bitmap bitmap =((BitmapDrawable) fragmentAddBinding.civProfile.getDrawable()).getBitmap();
            ((IMainActivity)context).onSavePerson(infoVM, relations, bitmap);

        }
        else  ((IMainActivity)context).onSavePerson(infoVM,relations,null);
    }

    public void setProfileImage(Bitmap profileImage) {
        fragmentAddBinding.civProfile.setImageBitmap(profileImage);
        isImageProfileChange=true;
    }
}
