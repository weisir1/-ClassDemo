package com.example.classdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.classdemo.R;
import com.example.classdemo.Util.DB;
import com.example.classdemo.Util.DBValue;
import com.example.classdemo.Util.UtilValue;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class RegistFragment1 extends Fragment {

    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userInfo)
    TextView userInfo;
    @BindView(R.id.passwd)
    EditText passwd;
    @BindView(R.id.surePs)
    EditText surePs;
    @BindView(R.id.passInfo)
    TextView passInfo;
    @BindView(R.id.nan)
    RadioButton nan;
    @BindView(R.id.nv)
    RadioButton nv;
    @BindView(R.id.btnRegist)
    Button btnRegist;
    @BindView(R.id.main)
    TextView main;
    @BindView(R.id.xiaHua)
    View xiaHua;
    @BindView(R.id.name)
    EditText name;
    private Unbinder bind;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.registfg1, container, false);
        bind = ButterKnife.bind(this, view);
        // Inflate the layout for this fragment

        listener();
        return view;
    }

    /*
     * 监听
     * */
    private void listener() {
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userInfo.setText("");
                passInfo.setText("");
                String nameString = name.getText().toString().trim();
                if (nameString.isEmpty()) {
                    Toast.makeText(context, "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<DBValue> list = new DB(context).getUserInfo("userName",charSequence.toString());
                if (list.size() > 0) {
                    Log.i("weiSir", "文字更改内容 " + charSequence.toString());

                    userInfo.setText("用户名已存在,请重新输入");
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userInfo.setText("");
                passInfo.setText("");
            }
        });

        passwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userInfo.setText("");
                passInfo.setText("");
                String userString = userName.getText().toString().trim();
                if (userString.isEmpty()) {
                    userInfo.setText("用户名不能为空");
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userInfo.setText("");
                passInfo.setText("");

            }
        });

        surePs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userInfo.setText("");
                passInfo.setText("");

                String userString = userName.getText().toString().trim();
                if (userString.isEmpty()) {
                    userInfo.setText("用户名不能为空");
                    return;
                }

                String passString = passwd.getText().toString().trim();
                if (passString.isEmpty()) {
                    passInfo.setText("密码框不能为空");
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String passString = passwd.getText().toString().trim();
                if (!TextUtils.equals(passString, charSequence)) {
                    passInfo.setText("两次密码不一致,请确认后重新输入");
                    return;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
                userInfo.setText("");
                passInfo.setText("");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();

    }

    @OnClick({R.id.btnRegist, R.id.main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnRegist:
                submit();
                break;
            case R.id.main:
              getFragmentManager().popBackStack();
              getFragmentManager().popBackStack();
                break;
        }
    }

    /*
     * 存储数据库
     * 防止直接点击登录按钮
     * */
    private void submit() {

        String userString = userName.getText().toString().trim();
        if (userString.isEmpty()) {
            userInfo.setText("用户名不能为空");
            return;
        }

        String nameString = name.getText().toString().trim();
        List<DBValue> list = new DB(context).getUserInfo("userName",userString);

        if (nameString.isEmpty()) {
            Toast.makeText(context, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (list.size() > 0) {
            userInfo.setText("用户名已存在,请重新输入");
            return;
        }

        String passString = passwd.getText().toString().trim();
        if (passString.isEmpty()) {
            passInfo.setText("密码框不能为空");
            return;
        }
        String sureString = surePs.getText().toString().trim();
        if (sureString.isEmpty()) {
            passInfo.setText("密码框不能为空");
            return;
        }

        if (!TextUtils.equals(passString, sureString)) {
            passInfo.setText("两次密码不一致,请确认后重新输入");
            return;
        }
        String sex = "男";
        if (nan.isChecked()) {
            sex = "男";
        } else {
            sex = "女";
        }

        String phone = UtilValue.dbValue.getPhone();
        if (!phone.isEmpty()) {
            new DB(context).setUserInfo(userString, phone, sex, userString, nameString);
            Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
            main.setText("点击此处返回登录界面");
            xiaHua.setVisibility(View.VISIBLE);
        }
    }
}
