package com.example.classdemo.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class Share {
    public static void saveFirst(Context context,boolean b){
        SharedPreferences s = context.getSharedPreferences("date",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = s.edit();
        e.putBoolean("status",b);
        e.commit();
    }
    public static boolean isFirst(Context context){
        SharedPreferences s = context.getSharedPreferences("date",Context.MODE_PRIVATE);
        return s.getBoolean("status",true);
    }

    public static void saveCheck1(Context context,boolean b){
        SharedPreferences s = context.getSharedPreferences("date",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = s.edit();
        e.putBoolean("save",b);
        e.commit();
    }

    public static boolean getCheck1(Context context){
        SharedPreferences s = context.getSharedPreferences("date",Context.MODE_PRIVATE);
        return s.getBoolean("save",false);
    }
    public static void saveUser(Context context, String userName, String passWord,String sex){
        SharedPreferences s = context.getSharedPreferences("date",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = s.edit();
        e.putString("user",userName);
        e.putString("pass",passWord);
        e.putString("sex",sex);
        e.commit();
    }
    public static String[] getUser(Context context){
        SharedPreferences s = context.getSharedPreferences("date",Context.MODE_PRIVATE);
        return new String[]{s.getString("user",""),s.getString("pass",""),s.getString("sex","")};
    }

    public static void mainLog(Context context, boolean flag){
        SharedPreferences s = context.getSharedPreferences("date",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = s.edit();
        edit.putBoolean("flag",flag);
        edit.commit();
    }

    public static boolean isLogin(Context context){
        SharedPreferences s = context.getSharedPreferences("date",Context.MODE_PRIVATE);
        return s.getBoolean("flag", false);
    }
    public static void saveCity(Context context, String city){
        SharedPreferences s = context.getSharedPreferences("date",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = s.edit();
        edit.putString("city",city);
        edit.commit();
    }
    public static String getCity(Context context){
        SharedPreferences s = context.getSharedPreferences("date",Context.MODE_PRIVATE);
        return s.getString("city", "太原");
    }
}
