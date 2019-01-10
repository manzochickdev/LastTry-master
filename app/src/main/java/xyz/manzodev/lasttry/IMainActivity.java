package xyz.manzodev.lasttry;

import android.graphics.Bitmap;

import xyz.manzodev.lasttry.AddEdit.InfoVM;
import xyz.manzodev.lasttry.Model.Address;
import xyz.manzodev.lasttry.Model.Model;

public interface IMainActivity {
    //search
    void onPersonSearchListener(String targetFragment);
    void onPersonSearchFinish(Model model,String targetFragment);

    void checkDb();

    //addedit
    void onAddPersonListener();
    void onSavePerson(InfoVM infoVM, Bitmap bitmap);

    void getRelationshipPicker();

    //place
    void getPlacePicker(InfoVM.OnDataListener onDataListener);
    void onPlacePickerResult(Address address);

    //imagepicker
    void getImagePicker(String targetFragment);
    void onImagePickerResult(Bitmap bitmap,String targetFragment);

}
