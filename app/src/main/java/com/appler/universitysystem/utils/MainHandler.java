package com.appler.universitysystem.utils;


import android.os.Handler;
import android.os.Looper;


public class MainHandler extends Handler {

    private static volatile MainHandler mainHandler;

    private MainHandler() {
        super(Looper.getMainLooper());
    }

    public static MainHandler getInstance() {
        if (null == mainHandler) {
            synchronized (MainHandler.class) {
                if (null == mainHandler) {
                    mainHandler = new MainHandler();
                }
            }
        }
        return mainHandler;
    }
}
