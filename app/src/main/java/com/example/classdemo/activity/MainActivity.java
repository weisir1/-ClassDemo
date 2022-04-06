package com.example.classdemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classdemo.BaseActivity;
import com.example.classdemo.R;
import com.example.classdemo.Util.AdapterValue;
import com.example.classdemo.Util.OkhttpUtil;
import com.example.classdemo.Util.Share;
import com.example.classdemo.Util.UtilValue;
import com.example.classdemo.fragment.MainFragment;
import com.example.classdemo.fragment.RelatedFragment;
import com.example.classdemo.fragment.WebFragment;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private View view;
    private AlertDialog show;
    private TextView exit;
    private TextView cancel;
    private ImageView drawable;
    private TextView loginStatus;
    private Toolbar toobar;
    private WebFragment web;


    @Override
    public int getlayout() {
        return R.layout.main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path = "https://vidio-1259536081.cos.ap-beijing.myqcloud.com/MyClassTest/6c14221abab26cd40087eb491125434f.mp4";
        try {
            URI uri = new URI(path);
            File file1 = new File(uri);
            File file = new File(UtilValue.SDPATH + "/video/v0.mp4");

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();   //如果没有创建父路径

                    FileOutputStream fileOutputStream = new FileOutputStream(file1);
                    FileInputStream stream = new FileInputStream(path);
                    Log.i("weiSir", "onCreate: " + Environment.getExternalStorageDirectory());

                    int length = 0;
                    byte[] bytes = new byte[1024 * 1024 * 1024];
                    while ((length = stream.read(bytes)) != -1){
                        fileOutputStream.write(length);
                        Log.i("weiSir", "handleMessage: " + length);
                    }
                }

            }
            } catch (Exception e) {
            e.printStackTrace();
        }

        toobar = findViewById(R.id.toobar);
        web = new WebFragment();
        TextView textView = (TextView) findViewById(R.id.title);
        textView.setText("项目主页");
        initToolBar(toobar, false);
        /*  同步抽屉布局和toolbar */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toobar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.syncState();
        drawable = navigation.getHeaderView(0).findViewById(R.id.headIcon);
        loginStatus = navigation.getHeaderView(0).findViewById(R.id.loginStatus);

        navigation.setNavigationItemSelectedListener(this);
        drawable.setOnClickListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new MainFragment()).commit();
    }


    //因为启动模式是singletask  所以在此回到main中是使用原先的  所以oncreate不会调用
    @Override
    protected void onStart() {
        showLogin();
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headIcon:
                startActivity(new Intent(this, LoginActicity.class));
                break;
            case R.id.exit:
                System.exit(1);
                break;
            case R.id.cancel:
                show.dismiss();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (UtilValue.web != null && UtilValue.web.canGoBack()) {    //如果当前网页可以返回  则返回上一层网页
            UtilValue.web.goBack();
        } else {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount > 0) {
                for (int i = 0; i < backStackEntryCount; i++) {
                    getSupportFragmentManager().popBackStack();
                }
            } else {
                exitDialog();
            }
        }

    }

    private void exitDialog() {
        view = View.inflate(this, R.layout.dialot, null);
        initView();
        show = new AlertDialog.Builder(this).setView(view).show();
    }


    private void initView() {

        exit = (TextView) view.findViewById(R.id.exit);
        cancel = (TextView) view.findViewById(R.id.cancel);
        exit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void showLogin() {
        if (!Share.isLogin(this)) {
            //如果未登录显示相应图标

            drawable.setImageDrawable(getResources().getDrawable(R.drawable.notlogin));
            loginStatus.setText("未登录");
        } else {
            //如果已登录  显示用户名和男女图标
            String[] user = Share.getUser(this);
            if (user[2].equals("男")) {
                drawable.setImageDrawable(getResources().getDrawable(R.drawable.boy));
            } else {
                drawable.setImageDrawable(getResources().getDrawable(R.drawable.gril));
            }
            loginStatus.setText(user[0]);
        }
    }

    /*  导航栏menu监听*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.zhuye:
                //弹出栈中所有碎片
                int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
                for (int i = 0; i < backStackEntryCount; i++) {
                    getSupportFragmentManager().popBackStack();
                }
                break;
            case R.id.guanyu:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new RelatedFragment()).addToBackStack(null).commit();
                break;

            /*注销登录  缓存值为false   头像显示未登录*/
            case R.id.logOut:
                Share.mainLog(this, false);
                showLogin();
                Toast.makeText(mcontext, "账户已注销", Toast.LENGTH_SHORT).show();
                break;

            case R.id.e:
                onBackPressed();
        }
        drawerLayout.closeDrawers();
        return false;
    }

}
