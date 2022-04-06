package com.example.classdemo.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.classdemo.BaseActivity;
import com.example.classdemo.R;
import com.example.classdemo.fragment.LoginFragment;

import butterknife.BindView;

public class LoginActicity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.framelayout)
    FrameLayout framelayout;

    private View view;
    private AlertDialog show;
    private TextView exit;
    private TextView cancel;

    @Override
    public int getlayout() {
        return R.layout.loginactivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new LoginFragment()).commitAllowingStateLoss();   /*替换到登录界面*/
        Toolbar toolbar = findViewById(R.id.toobar);
        toolbar.setNavigationIcon(R.drawable.back);
        TextView title = findViewById(R.id.title);
        title.setText("登录");
        initToolBar(toolbar, true);  /* 加载标题*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.exit:
//                System.exit(1);
//                break;
//            case R.id.cancel:
//                show.dismiss();
//                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    private void exitDialog() {
        view = View.inflate(this, R.layout.dialot, null);
        initView(view);
        show = new AlertDialog.Builder(this).setView(view).show();
    }


    private void initView(View view) {
        exit =  view.findViewById(R.id.exit);
        cancel =  view.findViewById(R.id.cancel);

        exit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }
}
