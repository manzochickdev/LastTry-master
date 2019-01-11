package xyz.manzodev.lasttry.AddEdit;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

import xyz.manzodev.lasttry.IMainActivity;
import xyz.manzodev.lasttry.Model.Address;
import xyz.manzodev.lasttry.Model.Model;

public class InfoVM extends BaseObservable {
    Model model;
    Address address;
    Context context;
    boolean isEdit;
    OnDataListener onDataListener=new OnDataListener() {
        @Override
        public void onPlaceBack(Address a) {
            address = a;
            notifyPropertyChanged(BR.address);
        }
    };


    public InfoVM(Model model, Address address, Context context) {
        this.model = model;
        this.address = address;
        this.context = context;
        isEdit = false;
    }


    public InfoVM(Context context) {
        this.context = context;
        this.model = new Model();
        isEdit = true;
    }

    public void getPlacePicker(){
        ((IMainActivity)context).getPlacePicker(onDataListener);
    }

    @Bindable
    public Address getAddress() {
        return address;
    }

    @Bindable
    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyPropertyChanged(BR.edit);
    }

    public Model getModel() {
        return model;
    }

    public interface OnDataListener{
        void onPlaceBack(Address address);
    }
}
