package com.su.chinaso.suchef.dish;

import android.content.Context;
import android.content.SharedPreferences;

import com.su.chinaso.suchef.SuApp;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private static final String STORAGE = "shop";

    public static Menu get() {
        return new Menu();
    }

    private SharedPreferences storage;
    private List<DishEntity> dishList = new ArrayList<>();

    private Menu() {
        storage = SuApp.getInstance().getApplicationContext().getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        DishManageDao manageDao = new DishManageDao();
        dishList = manageDao.getDishes();
    }

    public List<DishEntity> getData() {
        return dishList;
    }

    public boolean isRated(int itemId) {
        return storage.getBoolean(String.valueOf(itemId), false);
    }

    public void setRated(int itemId, boolean isRated) {
        storage.edit().putBoolean(String.valueOf(itemId), isRated).apply();
    }
}