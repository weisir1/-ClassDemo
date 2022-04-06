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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.classdemo.R;
import com.example.classdemo.Util.DB;
import com.example.classdemo.Util.DBValue;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ForgetFragment extends Fragment {


    @BindView(R.id.fgPhone)
    EditText fgPhone;
    @BindView(R.id.fgPassWord)
    EditText fgPassWord;
    @BindView(R.id.fgSure)
    EditText fgSure;
    @BindView(R.id.btnModif)
    Button btnModif;
    @BindView(R.id.main)
    TextView main;
    @BindView(R.id.xiaHua)
    View xiaHua;
    @BindView(R.id.phonInfo)
    TextView phonInfo;
    @BindView(R.id.fgPassInfo)
    TextView fgPassInfo;
    private Context context;
    private Unbinder bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        View view = inflater.inflate(R.layout.forgetfg, container, false);
        bind = ButterKnife.bind(this, view);
        fgPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                fgPassInfo.setText("");
                phonInfo.setText("");
            }
        });
        fgSure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                fgPassInfo.setText("");
                phonInfo.setText("");
            }
        });
        fgPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                fgPassInfo.setText("");
                phonInfo.setText("");
            }
        });
        return view;
    }


    @OnClick({R.id.btnModif, R.id.main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnModif:
                submit();
                break;
            case R.id.main:
                getFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void submit() {
        String phoneString = fgPhone.getText().toString().trim();
        if (phoneString.isEmpty()) {
            phonInfo.setText("请输入手机号");
            return;
        }
        List<DBValue> p = new DB(context).getUserInfo("phone", phoneString);

        if (p.size() == 0) {
            phonInfo.setText("没有找到您输入的手机号");
            return;
        }
        String passString = fgPassWord.getText().toString().trim();
        if (passString.isEmpty()) {
            fgPassInfo.setText("请输入密码");
            return;
        }
        String sureString = fgSure.getText().toString().trim();
        if (sureString.isEmpty()) {
            fgPassInfo.setText("请确认密码");
            return;
        }
        if (!TextUtils.equals(passString, sureString)) {
            fgPassInfo.setText("密码不一致,请确认密码后重新输入");
            return;
        }

        new DB(context).upDate(phoneString, passString);
        main.setText("密码修改成功,点击此处返回登录界面");
        xiaHua.setVisibility(View.VISIBLE);
    }
}
