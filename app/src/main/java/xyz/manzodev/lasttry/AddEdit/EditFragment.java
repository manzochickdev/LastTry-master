package xyz.manzodev.lasttry.AddEdit;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import java.util.ArrayList;

import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Relation;
import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentEditBinding;


public class EditFragment extends Fragment implements PopupMenu.OnMenuItemClickListener,View.OnClickListener {
    FragmentEditBinding fragmentEditBinding;
    CustomPagerAdapter customPagerAdapter;
    int id;
    Context context;
    boolean isImageProfileChange=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        fragmentEditBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit, container, false);
        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");
        fragmentEditBinding.setId(id);
        customPagerAdapter = new CustomPagerAdapter(id,getContext());
        fragmentEditBinding.viewpager.setAdapter(customPagerAdapter);
        fragmentEditBinding.detailTabs.setupWithViewPager(fragmentEditBinding.viewpager);

        fragmentEditBinding.civProfile.setOnClickListener(this);
        fragmentEditBinding.ivMenu.setOnClickListener(this);
        fragmentEditBinding.ivBack.setOnClickListener(this);
        fragmentEditBinding.tvCancel.setOnClickListener(this);
        fragmentEditBinding.tvOk.setOnClickListener(this);
        return fragmentEditBinding.getRoot();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.mi_edit:
                fragmentEditBinding.menuHandle.setVisibility(View.VISIBLE);
                customPagerAdapter.setIsEdit();
                return true;
            case R.id.mi_delete:
                ((IMainActivity) context).onRemovePersonListener(id);
                return true;

        }
        return false;
    }

    public void setProfileImage(Bitmap profileImage) {
        fragmentEditBinding.civProfile.setImageBitmap(profileImage);
        isImageProfileChange=true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_menu:{
                PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.edit_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(EditFragment.this);
                popup.show();
            }break;
            case R.id.iv_back:
                ((IMainActivity)context).onBackListener();
                break;
            case R.id.tv_cancel:
                ((IMainActivity)context).onReloadEdit(id);
                break;
            case R.id.tv_ok:
                handleUpdate();
                break;

            case R.id.civ_profile :
                ((IMainActivity)getContext()).getImagePicker(EditFragment.class.getSimpleName());
                break;
        }
    }

    private void handleUpdate() {
        customPagerAdapter.handleUpdate();
    }
}
