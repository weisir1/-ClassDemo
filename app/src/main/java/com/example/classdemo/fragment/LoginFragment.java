package com.example.classdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.classdemo.R;
import com.example.classdemo.Util.DB;
import com.example.classdemo.Util.DBValue;
import com.example.classdemo.Util.Share;
import com.example.classdemo.activity.LoginActicity;
import com.example.classdemo.activity.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends Fragment {
    private Context context;
    @BindView(R.id.user)
    EditText user;
    @BindView(R.id.passwd)
    EditText passwd;
    @BindView(R.id.wjPass)
    TextView wjPass;
    @BindView(R.id.checkSava)
    CheckBox checkSava;
    @BindView(R.id.btnLg)
    Button btnLg;
    @BindView(R.id.regist)
    TextView regist;

    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.loginfg, null);
        bind = ButterKnife.bind(this, view);
        context = getContext();


        checkSava.setOnCheckedChangeListener((compoundButton, b) -> {
            Share.saveCheck1(context, b);
        });
        if (Share.getCheck1(context)) {
            String[] u = Share.getUser(context);
            if (!u[1].isEmpty()) {
                user.setText(u[0]);
                passwd.setText(u[1]);
            }
        }

        checkSava.setChecked(Share.getCheck1(context));
        return view;
    }

    @OnClick({R.id.wjPass, R.id.btnLg, R.id.regist})
    public void onViewClickedg
            (View view) {
        switch (view.getId()) {
            case R.id.wjPass:
                getFragmentManager().beginTransaction().replace(R.id.framelayout, new ForgetFragment()).addToBackStack(null).commitAllowingStateLoss();
                break;
            case R.id.btnLg:
                submit();
                break;
            case R.id.regist:
                getFragmentManager().beginTransaction().replace(R.id.framelayout, new RegistFragment()).addToBackStack(null).commitAllowingStateLoss();
                break;
        }
    }

    private void submit() {
        String userString = user.getText().toString().trim();
        if (userString.isEmpty()) {
            Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String passWordString = passwd.getText().toString().trim();
        if (passWordString.isEmpty()) {
            Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        /*与数据库所存储得用户对比 */
        List<DBValue> list = new DB(context).getUserInfo("userName", userString);
        if (list.size() > 0) {
            /*如果等于0 用户名错误*/
            if (TextUtils.equals(userString, list.get(0).getUserName())) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                if (Share.getCheck1(context)) {
                    Share.saveUser(context, userString, passWordString, list.get(0).getSex());
                }
                /*保存登录状态*/
                Share.mainLog(context, true);

            } else {
                Toast.makeText(context, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(context, "您输入的用户名无效,请先进行注册", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();

    }
}
