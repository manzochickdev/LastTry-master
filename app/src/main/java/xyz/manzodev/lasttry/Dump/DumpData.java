package xyz.manzodev.lasttry.Dump;

import java.util.ArrayList;

import xyz.manzodev.lasttry.Model.Model;

public class DumpData {
    public static ArrayList<Model> getModelData(){
        ArrayList<Model> models = new ArrayList<>();
        models.add(new Model(0,"AAAAA","AAA"));
        models.add(new Model(0,"BBBBB","BBB"));
        models.add(new Model(0,"CCCCC","CCC"));
        models.add(new Model(0,"DDDDD","DDD"));
        models.add(new Model(0,"EEEEE","EEE"));
        models.add(new Model(0,"FFFFF","FFF"));
        return models;
    }
}
