package com.vik.typingassist.utils;

import java.util.ArrayList;

public class Utility {
    public static ArrayList<String> getJList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add("j");
        }
        return list;
    }
    public static ArrayList<String> getFList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add("f");
        }
        return list;
    }

    public static ArrayList<String> getFJList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("f");list.add("f");list.add("f");
        list.add("j");list.add("f");list.add("f");
        list.add("f");list.add("j");
        return list;
    }

    public static ArrayList<String> getFJSpaceList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("f");list.add("f");list.add(" ");
        list.add("j");list.add("f");list.add(" ");
        list.add("f");list.add("j");
        return list;
    }
}
