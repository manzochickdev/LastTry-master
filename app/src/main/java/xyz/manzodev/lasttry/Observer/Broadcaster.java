package xyz.manzodev.lasttry.Observer;

import android.content.Context;

import java.util.ArrayList;

public class Broadcaster implements Subject {
    int id;
    Context context;
    ArrayList<Observer> observers;

    public Broadcaster() {
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyChange() {
        for (Observer o : observers){
            o.onModelUpdate(id,context);
        }
    }

    public void updateModel(int id,Context context){
        this.context = context;
        this.id = id;
        notifyChange();
    }
}
