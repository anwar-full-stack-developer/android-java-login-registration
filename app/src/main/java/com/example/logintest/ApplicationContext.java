package com.example.logintest;

import android.app.Application;
import android.content.Context;

public class ApplicationContext extends Application
{
    private static volatile ApplicationContext instance = null;

    private ApplicationContext()
    {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        getInstance();
    }

//    public static synchronized Context getInstance()
//    {
//        if (null == instance)
//        {
//            instance = new ApplicationContext();
//        }
//
//        return instance;
//
////        if (instance == null) {
////            synchronized (ApplicationContext.class) {
////                if (instance == null) {
////                    instance = new ApplicationContext ();
////                }
////            }
////        }
////
////        return instance;
//    }
}
