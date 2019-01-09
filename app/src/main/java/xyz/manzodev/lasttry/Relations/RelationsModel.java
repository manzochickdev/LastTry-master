package xyz.manzodev.lasttry.Relations;

import java.util.ArrayList;

import xyz.manzodev.lasttry.Model.Model;

public class RelationsModel {
    public ArrayList<Model> models;
    public int root = -1;

    public RelationsModel(ArrayList<Model> models) {
        this.models = models;
    }
}
