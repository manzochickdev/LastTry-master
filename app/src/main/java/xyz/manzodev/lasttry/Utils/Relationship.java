package xyz.manzodev.lasttry.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Relationship {
    public static final String CHILD="Child";
    public static final String SIBLING="Sibling";
    public static final String PARENT="Parent";
    public static final String SPOUSE="Spouse";

    private static ArrayList<String> relationship = new ArrayList<>(Arrays.asList(CHILD,SIBLING,PARENT,SPOUSE));

    public static int convertRelationshipInt(String s){
        for (String rela : relationship){
            if (rela.equals(s)) return relationship.indexOf(rela);
        }
        return -1;
    }

    public static String convertRelationshipString(int i){
        return relationship.get(i);
    }

}
