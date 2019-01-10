package xyz.manzodev.lasttry.Observer;

public interface Subject {
    void addObserver(Observer o);
    void notifyChange();
}
