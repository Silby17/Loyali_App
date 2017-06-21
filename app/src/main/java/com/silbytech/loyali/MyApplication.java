package com.silbytech.loyali;

import android.app.Application;
import android.content.SharedPreferences;

import java.io.File;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class MyApplication extends Application {
    private static MyApplication instance;
    public static final String PREFS = "prefs";
    private String MEDIA_URL = "http://192.168.1.14:8000";


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //Get shared preferences
        SharedPreferences preferences = getSharedPreferences(PREFS, 0);
        //Add MEDIA_URL to shared preferences
        preferences.edit().putString("media_url", MEDIA_URL).apply();
    }

    public static MyApplication getInstance() {return instance;}


    public void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }
        return deletedAll;
    }

    //Getter for MEDIA_URL
    public String getMEDIA_URL() {
        return MEDIA_URL;
    }
}