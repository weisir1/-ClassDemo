package com.example.classdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class RegistFragment extends Fragment {
    @BindView(R.id.rgPhone)
    EditText rgPhone;
    @BindView(R.id.next)
    Button next;
    private Context context;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = View.inflate(context, R.layout.registfg, null);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.next)
    public void onViewClicked() {
        submit();
    }

    /*
     * 手机号格式判断
     * */
    private void submit() {
        String phoneString = rgPhone.getText().toString().trim();
        if (phoneString.isEmpty()) {
            Toast.makeText(context, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (phoneString.length() != 11) {
            Toast.makeText(context, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        List<DBValue> p = new DB(context).getUserInfo("phone", phoneString);
        if (p.size() >0){
            Toast.makeText(context, "手机号已注册", Toast.LENGTH_SHORT).show();
            return;
        }
        UtilValue.dbValue.setPhone(phoneString);
        getFragmentManager().beginTransaction().replace(R.id.framelayout,new RegistFragment1()).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

}
