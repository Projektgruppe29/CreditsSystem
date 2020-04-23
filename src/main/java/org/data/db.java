package org.data;

import org.domain.Production;

import java.util.ArrayList;

public class db {
    private static ArrayList<Production> list = new ArrayList<>();

    public static ArrayList<Production> getList() {
        return list;
    }

    public static void setList(ArrayList<Production> list) {
        db.list = list;
    }
}
