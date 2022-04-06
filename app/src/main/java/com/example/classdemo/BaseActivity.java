package com.example.classdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    public Context mcontext;
    public Activity mactivity;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getlayout());
        ButterKnife.bind(this);
        mcontext = this;
        mactivity = this;
    }



    public void initToolBar(Toolbar toolbar, boolean homeEnable) {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnable);
    }



    public abstract int getlayout();

}
