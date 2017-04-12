package com.su.chinaso.suchef;

import android.app.Application;
import android.content.Context;

import com.su.chinaso.suchef.dish.GreenDaoManager;
import com.su.chinaso.suchef.view.DiscreteScrollViewOptions;

/**
 * Created by chinaso on 2017/3/15.
 */

public class SuApp extends Application {
    private static SuApp instance;
    private static Context mContext;

    public static SuApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DiscreteScrollViewOptions.init(this);
        mContext = getApplicationContext();
        GreenDaoManager.getInstance();
    }

    public static Context getContext() {
        return mContext;
    }
}
