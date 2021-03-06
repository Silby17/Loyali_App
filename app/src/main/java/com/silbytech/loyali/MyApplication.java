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
    private String MEDIA_URL = "http://34.250.74.48";


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


    /*******************************************************************************
     * This method will clear all of the Application data from the device
     *******************************************************************************/
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


    /*******************************************************************************
     * This method will delete all file of the app from the device
     * @param file - file to remove
     * @return - true at completion
     ********************************************************************************/
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


    /*******************************************************************************
     * Gets the MEDIA_URL
     * @return - MEDIA_URL
     *******************************************************************************/
    public String getMEDIA_URL() {
        return MEDIA_URL;
    }
}