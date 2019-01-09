package xyz.manzodev.lasttry;

import xyz.manzodev.lasttry.Model.Model;

public interface IMainActivity {
    void onPersonSearchListener(String targetFragment);
    void onPersonSearchFinish(Model model,String targetFragment);

    void checkDb();

    void onAddPersonListener();

    void getRelationshipPicker();
}
