package com.example.classdemo.Util;

import android.graphics.drawable.Drawable;

public class AdapterValue {
    private String appName;
    private Drawable imgResourse;

    public AdapterValue(String appName, Drawable imgResourse) {
        this.appName = appName;
        this.imgResourse = imgResourse;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getImgResourse() {
        return imgResourse;
    }

    public void setImgResourse(Drawable imgResourse) {
        this.imgResourse = imgResourse;
    }
}
