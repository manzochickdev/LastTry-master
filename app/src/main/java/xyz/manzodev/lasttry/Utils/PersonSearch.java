package xyz.manzodev.lasttry.Utils;

import android.content.Context;

import java.util.ArrayList;

import xyz.manzodev.lasttry.Dump.DumpData;
import xyz.manzodev.lasttry.Model.Model;

public class PersonSearch {
    private static PersonSearch personSearch;
    ArrayList<Model> models;
    ArrayList<Model> temp;

    public PersonSearch(Context context) {
        models = getModels();
        temp = new ArrayList<>();
    }

    public static PersonSearch getInstance(Context context){
        if (personSearch==null) personSearch = new PersonSearch(context);
        return personSearch;
    }

    public ArrayList<Model> onSearchListener(String q) {
        temp.clear();
        if  (q==null || q.length()==0){
            temp.addAll(models);
        }
        else {
            for (Model m : models){
                if (m.getName().toLowerCase().contains(q.toLowerCase())){
                    temp.add(m);
                }
            }
        }
        return temp;
    }

    public ArrayList<Model> getModels() {
        return DumpData.getModelData();
    }
}
