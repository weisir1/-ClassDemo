package com.example.classdemo.Util;

import android.os.Environment;
import android.webkit.WebView;

import java.io.File;
import java.net.URL;

public class UtilValue {
    public static DBValue dbValue = new DBValue(1,"","","","","");

    public static final String URL = "https://free-api.heweather.net/s6/weather//forecast?location=";   //天气访问地址

    public static final String URLKEY = "&key=98c4d2f00d71400bb81a79df6411f239";   //和风天气key值

    public static WebView web;   //全局web

    public static int VIDIOSIZE = 28;

    public  static final String SDPATH = Environment.getExternalStorageDirectory().toString() + File.separator + "data"+File.separator;

    public static String VIDEOPATH = "https://vidio-1259536081.cos.ap-beijing.myqcloud.com/MyClassTest/v";

}
