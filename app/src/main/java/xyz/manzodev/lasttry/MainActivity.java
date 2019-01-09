package xyz.manzodev.lasttry;

import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import xyz.manzodev.lasttry.AddEdit.AddFragment;
import xyz.manzodev.lasttry.Dump.DBDataFragment;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.People.PeopleFragment;
import xyz.manzodev.lasttry.Place.PlaceViewFragment;
import xyz.manzodev.lasttry.Relations.RelationsFragment;
import xyz.manzodev.lasttry.Utils.Search.PersonSearchFragment;
import xyz.manzodev.lasttry.Utils.SearchBottom.SearchBottomFragment;

public class MainActivity extends AppCompatActivity implements IMainActivity,BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    public void getRelationshipPicker() {
        SearchBottomFragment searchBottomFragment = new SearchBottomFragment();
        searchBottomFragment.show(getSupportFragmentManager(),SearchBottomFragment.class.getSimpleName());
    }
}
