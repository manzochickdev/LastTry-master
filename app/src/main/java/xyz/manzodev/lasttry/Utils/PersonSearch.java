package xyz.manzodev.lasttry.Utils;

import android.app.Person;
import android.content.Context;

import java.util.ArrayList;

import xyz.manzodev.lasttry.Dump.DumpData;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.Observer.Observer;
import xyz.manzodev.lasttry.Observer.Subject;

public class PersonSearch implements Observer {
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

    public PersonSearchDistinc distinc(ArrayList<Integer> listId){
        return new PersonSearchDistinc(listId);
    }

    @Override
    public void onModelUpdate(int id, Context context) {
        personSearch = new PersonSearch(context);
    }

    public class PersonSearchDistinc{
        private ArrayList<Integer> listId;
        private ArrayList<Model> distinc;
        private ArrayList<Model> temp;

        public PersonSearchDistinc(ArrayList<Integer> listId) {
            this.listId = listId;
            distinc = getList();
            temp = new ArrayList<>();
        }

        private ArrayList<Model> getList() {
            ArrayList<Model> list = new ArrayList<>();
            for (Model m : models){
                Boolean b=false;
                for (int i : listId){
                    if (m.getId()==i){
                        b=true;
                    }
                }
                if (b==false) list.add(m);
            }
            return list;
        }

        public ArrayList<Model> getModels() {
            return distinc;
        }

        public ArrayList<Model> onSearchListener(String q) {
            temp.clear();
            if  (q==null || q.length()==0){
                temp.addAll(distinc);
            }
            else {
                for (Model m : distinc){
                    if (m.getName().toLowerCase().contains(q.toLowerCase())){
                        temp.add(m);
                    }
                }
            }
            return temp;
        }


    }
}
