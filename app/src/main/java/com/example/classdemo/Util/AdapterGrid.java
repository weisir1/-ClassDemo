package com.example.classdemo.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.classdemo.R;
import com.example.classdemo.activity.VideoActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;

public class AdapterGrid extends BaseAdapter {
    private List<String> list;
    private Context context;
    private ImageView resourse;
    private TextView spInfo;

    public AdapterGrid(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

            view = View.inflate(context, R.layout.griditem, null);
            resourse = view.findViewById(R.id.resourse);
            spInfo = view.findViewById(R.id.spInfo);
        Log.i("weiSir", "getView: " + i);
        spInfo.setText(list.get(i));
        view.setOnClickListener(view1 -> {
            String path = UtilValue.VIDEOPATH + i;
            Intent intent = new Intent(context, VideoActivity.class);
            intent.putExtra("path", path);
            context.startActivity(intent);
        });
        return view;
    }
}
