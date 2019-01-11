package xyz.manzodev.lasttry;

import android.graphics.Bitmap;

import java.util.ArrayList;

import xyz.manzodev.lasttry.AddEdit.InfoVM;
import xyz.manzodev.lasttry.AddEdit.RelationshipVM;
import xyz.manzodev.lasttry.Model.Address;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.Model.Relation;

public interface IMainActivity {
    //search
    void onPersonSearchListener(String targetFragment);
    void onPersonSearchFinish(Model model,String targetFragment);

    void checkDb();

    //addedit
    void onAddPersonListener();
    void onEditPersonListener(int id);
    void onReloadEdit(int id);
    void onRemovePersonListener(int id);
    void onSavePerson(InfoVM infoVM, ArrayList<Relation> relations, Bitmap bitmap);

    void getRelationshipPicker(RelationshipVM.OnDataListener onDataListener, ArrayList<Integer> listId);
    void onRelationshipResult(Relation relation);

    //place
    void getPlacePicker(InfoVM.OnDataListener onDataListener);
    void onPlacePickerResult(Address address);

    //imagepicker
    void getImagePicker(String targetFragment);
    void onImagePickerResult(Bitmap bitmap,String targetFragment);

    void notifyDBChange();

    void onBackListener();

}
