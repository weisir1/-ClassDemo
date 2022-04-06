package com.example.classdemo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.classdemo.R;
import com.example.classdemo.Util.UtilValue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WebFragment extends Fragment {

    @BindView(R.id.webEdit)
    EditText webEdit;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.jump)
    Button jump;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.linear)
    LinearLayout linear;
    private Activity context;
    private View view;
    private String URL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.bind(this, view);

        UtilValue.web = webView;
        webEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                webEdit.setText("https://");
            }
        });
        settings();
        return view;
    }


    /*   因为使用原生浏览器访问  要对webview设置一些属性*/
    private void settings() {
        String[] stringArray = getResources().getStringArray(R.array.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinnertxt, stringArray);
        spinner.setAdapter(adapter);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);  //js可以打开window窗体
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(true);   //缩放
        settings.setUseWideViewPort(true);   //按任意比例缩放
        settings.setLoadWithOverviewMode(true);   //适应窗口
        settings.setAppCacheEnabled(true);    //缓存
        webView.setWebViewClient(new WebViewClient());   //拦截搜索窗口  使用当前界面显示
        webView.setWebChromeClient(new WebChromeClient() {

        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 1:
                        URL = "https://www.so.com/";
                        break;
                    case 2:
                        URL = "https://cn.bing.com/";
                        break;
                    case 3:
                        URL = "https://www.zhihuishu.com/";
                        break;
                    case 4:
                        URL = "https://www.sohu.com";
                        break;
                    case 5:
                        URL = "https://www.jd.com/";
                        break;

                }
                webView.loadUrl(URL);
                URL = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this, view).unbind();
    }

    @OnClick(R.id.jump)
    public void onViewClicked() {
        String trim = webEdit.getText().toString().trim();
        if (!trim.isEmpty()) {
            webView.loadUrl(trim);
        }
        webEdit.setText("");
    }


}
