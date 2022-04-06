package com.example.classdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.classdemo.Util.Share;
import com.example.classdemo.activity.LoginActicity;
import com.example.classdemo.activity.MainActivity;

public class GuideActivity extends BaseActivity {
    @Override
    public int getlayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = new Intent(GuideActivity.this, MainActivity.class);

        if (Share.isFirst(this)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Share.saveFirst(GuideActivity.this,false);
                    startActivity(intent);
                    finish();
                }
            },3000);
        }else{
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {

    }
}
