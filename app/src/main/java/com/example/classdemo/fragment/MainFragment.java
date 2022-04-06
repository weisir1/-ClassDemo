package com.example.classdemo.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.classdemo.R;
import com.example.classdemo.Util.AdapterGrid;
import com.example.classdemo.Util.AdapterValue;
import com.example.classdemo.Util.MyListView;
import com.example.classdemo.Util.UtilValue;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    private MyListView gridView;
    private RecyclerView recyclerView;
    private List<AdapterValue> recy = new ArrayList<>();
    private List<String> gri = new ArrayList<>();
    private Activity context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*      加载视频文字说明      */
        for (int i = 0; i < UtilValue.VIDIOSIZE; i++) {
            gri.add("视频" + (i+1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View view = inflater.inflate(R.layout.mainin, container, false);
        adapter(view);
        return view;
    }


    private void adapter(View v) {

        gridView = (MyListView) v.findViewById(R.id.gridView);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        Resources resources = getResources();
        recy.clear();
        recy.add(new AdapterValue("天气显示", resources.getDrawable(R.drawable.weather)));
        recy.add(new AdapterValue("地图定位", resources.getDrawable(R.drawable.map)));
        recy.add(new AdapterValue("网页浏览", resources.getDrawable(R.drawable.web)));
        Adapter adapter = new Adapter(recy, context);
        /*   设置为水平扩展   */
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        gridView.setAdapter(new AdapterGrid(gri, context));
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        public List<AdapterValue> list;
        private Activity activity;


        public Adapter(List<AdapterValue> list, Activity context) {
            this.list = list;
            activity = context;
        }

        private ImageView logo;
        private TextView appName;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.recyitem, null);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            logo.setImageDrawable(list.get(position).getImgResourse());
            appName.setText(list.get(position).getAppName());
            holder.itemView.setOnClickListener(view -> {
                switch (position) {
                    case 0:
                        getFragmentManager().beginTransaction().replace(R.id.mainFrame, new WeatherFragment()).addToBackStack(null).commit();
                        break;
                    case 1:
                        getFragmentManager().beginTransaction().replace(R.id.mainFrame, new MapFragment()).addToBackStack(null).commit();
                        break;
                    case 2:
                        getFragmentManager().beginTransaction().replace(R.id.mainFrame, new WebFragment()).addToBackStack(null).commit();
                        break;
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                logo = itemView.findViewById(R.id.logo);
                appName = itemView.findViewById(R.id.appName);
            }
        }
    }
}
