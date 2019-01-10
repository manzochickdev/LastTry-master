package xyz.manzodev.lasttry.Model;

import java.util.Calendar;

public class Model {
    public int id;
    public String name,dispRela;

    public Model(int id, String name, String dispRela) {
        this.id = id;
        this.name = name;
        this.dispRela = dispRela;
    }

    public Model() {
    }

    public Model(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDispRela() {
        return dispRela;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDispRela(String dispRela) {
        this.dispRela = dispRela;
    }

    public static int createId(){
        Calendar c = Calendar.getInstance();
        int id = (int) c.getTimeInMillis();
        return id;
    }

}
