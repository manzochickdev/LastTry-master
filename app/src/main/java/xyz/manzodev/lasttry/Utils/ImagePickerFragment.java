package xyz.manzodev.lasttry.Utils;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import xyz.manzodev.lasttry.R;
import xyz.manzodev.lasttry.databinding.FragmentImagePickerBinding;

import static xyz.manzodev.lasttry.Utils.Req.PERMISSIONS_REQUEST_CAMERA;
import static xyz.manzodev.lasttry.Utils.Req.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
import static xyz.manzodev.lasttry.Utils.Req.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY;

public class ImagePickerFragment extends BottomSheetDialogFragment {
    FragmentImagePickerBinding fragmentImagePickerBinding;
    boolean mCameraPermissionGranted=false;
    boolean mStoragePermissionGranted=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentImagePickerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_image_picker, container, false);
        fragmentImagePickerBinding.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePermission(R.id.btnCamera);
                if (mCameraPermissionGranted){
                    pickImage(R.id.btnCamera);
                }


            }
        });

        fragmentImagePickerBinding.btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePermission(R.id.btnGallery);
                if (mStoragePermissionGranted){
                    pickImage(R.id.btnGallery);
                }


            }
        });

        fragmentImagePickerBinding.btnStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePermission(R.id.btnStorage);
                if (mStoragePermissionGranted){
                    pickImage(R.id.btnStorage);
                }


            }
        });
        fragmentImagePickerBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



        return fragmentImagePickerBinding.getRoot();
    }

    private void pickImage(int id) {
        switch (id){
            case R.id.btnCamera :{
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,9191);
            } break;
            case R.id.btnGallery:{
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent.createChooser(intent,"Select"),9292);
            } break;
            case R.id.btnStorage:{
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"Select"),9292);
            } break;
        }
    }

    private void handlePermission(int id) {
        switch (id){
            case R.id.btnCamera:{
                if (!(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(
                            new String[]{android.Manifest.permission.CAMERA},
                            PERMISSIONS_REQUEST_CAMERA);
                }
                else mCameraPermissionGranted=true;
            }
            break;
            case R.id.btnGallery :{
                if (!(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY);
                }
                else mStoragePermissionGranted=true;
            }
            break;
            case R.id.btnStorage :{
                if (!(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                else mStoragePermissionGranted=true;
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSIONS_REQUEST_CAMERA :{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCameraPermissionGranted = true;
                    pickImage(R.id.btnCamera);
                }
                else {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                        toSetting();
                    }
                }
            }
            break;
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY :{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mStoragePermissionGranted = true;
                    pickImage(R.id.btnGallery);
                }
                else {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                        toSetting();
                    }
                }
            }break;
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE : {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mStoragePermissionGranted = true;
                    pickImage(R.id.btnStorage);
                }
                else {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                        toSetting();
                    }
                }
            }
            break;
        }
    }

    private void toSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, 993);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            switch (requestCode){
                case 9191:
                    try{
                        Bundle extras = data.getExtras();
                        Bitmap bitma1p = (Bitmap) extras.get("data");
                        //todo onbitmap back
                        dismiss();
                    }
                    catch (Exception e){
                    }
                    finally {
                    }
                    break;
                case 9292:
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                        //todo onbitmap back
                        dismiss();
                    }
                    catch (IOException e) { }
                    finally { }
                    break;
            }
        }
    }
}
