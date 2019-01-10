package xyz.manzodev.lasttry;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import xyz.manzodev.lasttry.AddEdit.AddFragment;
import xyz.manzodev.lasttry.AddEdit.InfoVM;
import xyz.manzodev.lasttry.Dump.DBDataFragment;
import xyz.manzodev.lasttry.Model.Address;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.People.PeopleFragment;
import xyz.manzodev.lasttry.Place.PlaceViewFragment;
import xyz.manzodev.lasttry.Relations.RelationsFragment;
import xyz.manzodev.lasttry.Utils.FileUtils;
import xyz.manzodev.lasttry.Utils.ImagePickerFragment;
import xyz.manzodev.lasttry.Utils.Req;
import xyz.manzodev.lasttry.Utils.Search.PersonSearchFragment;
import xyz.manzodev.lasttry.Utils.SearchBottom.SearchBottomFragment;

import static xyz.manzodev.lasttry.Utils.Req.PLACE_PICKER;

public class MainActivity extends AppCompatActivity implements IMainActivity,BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    DatabaseHandle databaseHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHandle = new DatabaseHandle(MainActivity.this);

        PeopleFragment peopleFragment = new PeopleFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_act_container,peopleFragment,PeopleFragment.class.getSimpleName())
                .commit();

        bottomNavigationView = findViewById(R.id.bnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.mi_people:
                PeopleFragment peopleFragment = new PeopleFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_act_container,peopleFragment,PeopleFragment.class.getSimpleName())
                        .addToBackStack(PeopleFragment.class.getSimpleName())
                        .commit();
                return true;
            case R.id.mi_location:{
                PlaceViewFragment placeViewFragment = new PlaceViewFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_act_container,placeViewFragment,PlaceViewFragment.class.getSimpleName())
                        .addToBackStack(PlaceViewFragment.class.getSimpleName())
                        .commit();
            }return true;
            case R.id.mi_relationship:{
                RelationsFragment relationsFragment = new RelationsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_act_container,relationsFragment,RelationsFragment.class.getSimpleName())
                        .addToBackStack(RelationsFragment.class.getSimpleName())
                        .commit();
            }return true;
        }
        return false;
    }

    @Override
    public void onPersonSearchListener(String targetFragment) {
        Bundle bundle = new Bundle();
        bundle.putString("target",targetFragment);
        PersonSearchFragment personSearchFragment = new PersonSearchFragment();
        personSearchFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container,personSearchFragment,PersonSearchFragment.class.getSimpleName())
                .addToBackStack(PersonSearchFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onPersonSearchFinish(Model model, String targetFragment) {
        onBackPressed();
        if (targetFragment.equals(RelationsFragment.class.getSimpleName())){
            RelationsFragment relationsFragment = (RelationsFragment) getSupportFragmentManager().findFragmentByTag(targetFragment);
            relationsFragment.onModelBack(model);
        }
    }

    @Override
    public void checkDb() {
        DBDataFragment dbDataFragment = new DBDataFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,dbDataFragment,DBDataFragment.class.getSimpleName())
                .addToBackStack(DBDataFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onAddPersonListener() {
        AddFragment addFragment = new AddFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,addFragment,AddFragment.class.getSimpleName())
                .addToBackStack(AddFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onSavePerson(InfoVM infoVM, Bitmap bitmap) {
        int id = Model.createId();
        Model model = infoVM.getModel();
        databaseHandle.addPerson(model,id);

        Address address = infoVM.getAddress();
        if (address!=null) databaseHandle.addAddress(address,id);
        if (bitmap!=null) FileUtils.getInstance(MainActivity.this).saveBitmap(id,bitmap);
    }

    @Override
    public void getRelationshipPicker() {
        SearchBottomFragment searchBottomFragment = new SearchBottomFragment();
        searchBottomFragment.show(getSupportFragmentManager(),SearchBottomFragment.class.getSimpleName());
    }


    InfoVM.OnDataListener onDataListener;
    @Override
    public void getPlacePicker(InfoVM.OnDataListener onDataListener) {
        this.onDataListener = onDataListener;
        Bundle bundle = new Bundle();
        bundle.putString("mode",PLACE_PICKER);
        PlaceViewFragment placeViewFragment = new PlaceViewFragment();
        placeViewFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,placeViewFragment,PlaceViewFragment.class.getSimpleName())
                .addToBackStack(PlaceViewFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onPlacePickerResult(Address address) {
        onDataListener.onPlaceBack(address);
        onBackPressed();
    }

    @Override
    public void getImagePicker(String targetFragment) {
        Bundle bundle = new Bundle();
        bundle.putString("target",targetFragment);
        ImagePickerFragment imagePickerFragment = new ImagePickerFragment();
        imagePickerFragment.setArguments(bundle);
        imagePickerFragment.show(getSupportFragmentManager(),ImagePickerFragment.class.getSimpleName());
    }

    @Override
    public void onImagePickerResult(Bitmap bitmap,String targetFragment) {
        if (targetFragment.equals(AddFragment.class.getSimpleName())){
            AddFragment addFragment = (AddFragment) getSupportFragmentManager().findFragmentByTag(AddFragment.class.getSimpleName());
            addFragment.setProfileImage(bitmap);
        }
    }
}
