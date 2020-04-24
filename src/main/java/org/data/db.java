package org.data;

import org.domain.Credits;
import org.domain.Production;

import java.util.ArrayList;

public class db {

    //For Production
    private static ArrayList<Production> list = new ArrayList<>();

    public static ArrayList<Production> getList() {
        return list;
    }

    public static void setList(ArrayList<Production> list) {
        db.list = list;
    }

    //For Credits
    private static ArrayList<Credits> creditsList = new ArrayList<>();

    public static ArrayList<Credits> getCreditsList() {
        return creditsList;
    }

    public static void setCreditsList(ArrayList<Credits> list) {
        db.creditsList = list;
    }
}
