package com.example.classdemo.fragment;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.classdemo.R;
import com.example.classdemo.Util.Share;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapFragment extends Fragment {

    @BindView(R.id.map)
    TextureMapView map;
    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.mpSpinner)
    Spinner mpSpinner;
    private View view;
    private AMap aMap;
    private AMapLocationClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        map.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = map.getMap();
        }
        String[] stringArray = getResources().getStringArray(R.array.map);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinnertxt,stringArray);
        mpSpinner.setAdapter(adapter);

        mpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                    case 1:
                        aMap.setMapType(AMap.MAP_TYPE_NAVI);  //导航模式
                        break;
                    case 2:
                        aMap.setMapType(AMap.MAP_TYPE_NIGHT);  //夜景模式
                        break;
                    case 3:
                        aMap.setMapType(AMap.MAP_TYPE_NORMAL);  //白昼模式(默认)
                        break;
                    case 4:
                        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);   //卫星模式
                        break;
                    case 5:
                        aMap.setMapType(AMap.MAP_TYPE_BUS);   //公交模式
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mapSettings();   //地图属性
        Location();   //定位监听

        return view;
    }

    private void mapSettings() {
        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.interval(2000);    //设置定位间隔
        locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);   //设置定位方式为连续定位
        locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);   //设置定位蓝点随设备变动
        aMap.getUiSettings().setMyLocationButtonEnabled(true);    //设置定位按钮显示
        aMap.setMyLocationEnabled(true);    //设置定位蓝点显示
    }

    private void Location() {
        AMapLocationClientOption option;
        client = new AMapLocationClient(getActivity());
        option = new AMapLocationClientOption();

        option.setNeedAddress(true);  //返回地址

        client.setLocationOption(option);
        client.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        Share.saveCity(getActivity(), aMapLocation.getCity());
                        info.setText(aMapLocation.getProvince() + aMapLocation.getCity()
                                + aMapLocation.getDistrict()
                                + aMapLocation.getStreet() + aMapLocation.getStreetNum() + aMapLocation.getFloor());
                    }
                }
            }
        });
        client.startLocation();
    }

    @Override
    public void onResume() {
        map.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (map != null){
            map.onDestroy();
        }
        if (client!=null){
            client.stopAssistantLocation();
        }
        ButterKnife.bind(this, view).unbind();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        map.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
