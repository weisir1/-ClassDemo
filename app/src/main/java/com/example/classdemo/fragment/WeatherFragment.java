package com.example.classdemo.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.classdemo.R;
import com.example.classdemo.Util.OkhttpUtil;
import com.example.classdemo.Util.Share;
import com.example.classdemo.Util.UtilValue;
import com.example.classdemo.Util.WeatherValue;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WeatherFragment extends Fragment {


    @BindView(R.id.wtImg)
    ImageView wtImg;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.feng)
    TextView feng;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.shidu)
    TextView shidu;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.refresh)
    ImageView refresh;
    @BindView(R.id.linchart)
    LineChart linchart;
    @BindView(R.id.barchart)
    BarChart barchart;
    @BindView(R.id.edCity)
    EditText edCity;
    @BindView(R.id.weatherInfo)
    TextView weatherInfo;

    private View view;
    private Gson gson;
    private List<String> lineBottom = new ArrayList<>();
    private List<Integer> max = new ArrayList<>();
    private List<Integer> min = new ArrayList<>();
    private List<List<Integer>> list = new ArrayList<>();

    private List<Float> humi = new ArrayList<>();
    private List<Float> pres = new ArrayList<>();
    private List<Float> pcpn = new ArrayList<>();
    private List<Float> vis = new ArrayList<>();
    private List<Float> uv_index = new ArrayList<>();
    private List<List<Float>> list2 = new ArrayList<>();
    private List<String> barBottom1 = new ArrayList<>();
    private int[] color = new int[]{Color.parseColor("#4EB1FF"), Color.parseColor("#FF4CAF50")
            , Color.parseColor("#03365F")
            , Color.BLUE, Color.parseColor("#E133FF"), Color.parseColor("#676B6B")};

    private String updatTime = "";
    private String cityName;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 0:
                    weather = gson.fromJson(msg.obj.toString(), WeatherValue.class);
                    updatTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    updata();
                    break;
            }
        }
    };
    private WeatherValue weather;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        gson = new Gson();
        cityName = Share.getCity(getContext()).substring(0, 2);
        data();

        /*请求网络获取数据*/
        OkhttpUtil.send(UtilValue.URL+cityName+UtilValue.URLKEY, handler, 0);
        return view;
    }

    private void data() {
        list.add(max);
        list.add(min);

        list2.add(humi);  //湿度
        list2.add(pres);  //压强
        list2.add(pcpn);  //降水量
        list2.add(uv_index);  //紫外线强度
        list2.add(vis);  //能见度

        for (int i = 0; i < 7; i++) {
            min.add(1);
            max.add(1);
            lineBottom.add("a");

            humi.add(50f);
            pres.add(1000f);
            pcpn.add(1f);
            uv_index.add(1000f);
            vis.add(25f);
            barBottom1.add("a");
        }
        showLinechar();
        showBarchart();
    }

    private void updata() {

        List<WeatherValue.HeWeather6Bean.DailyForecastBean> daily_forecast = weather.getHeWeather6().get(0).getDaily_forecast();
        for (int i = 0; i < daily_forecast.size(); i++) {
            /*      折线图数据       */

            max.set(i, Integer.parseInt(daily_forecast.get(i).getTmp_max()));
            min.set(i, Integer.parseInt(daily_forecast.get(i).getTmp_min()));
            lineBottom.set(i, daily_forecast.get(i).getDate());

            /*      条形图数据       */
            humi.set(i, Float.parseFloat(daily_forecast.get(i).getHum()));
            pres.set(i, Float.parseFloat(daily_forecast.get(i).getPres()));
            pcpn.set(i, Float.parseFloat(daily_forecast.get(i).getPcpn()));
            uv_index.set(i, Float.parseFloat(daily_forecast.get(i).getUv_index()));
            vis.set(i, Float.parseFloat(daily_forecast.get(i).getVis()));
            switch (i) {
                case 0:
                    barBottom1.set(i, "今天/" + daily_forecast.get(i).getCond_txt_d());
                    break;
                case 1:
                    barBottom1.set(i, "明天/" + daily_forecast.get(i).getCond_txt_d());
                    break;


            }
            if (i > 1) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + i);
                String week = new SimpleDateFormat("EEE").format(calendar.getTime());
                barBottom1.set(i, week + "/" + daily_forecast.get(i).getCond_txt_d());

            }
        }

        if (temp != null) {
            showHead(daily_forecast);
            showLinechar();
            showBarchart();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this, view).unbind();
    }

    @OnClick(R.id.refresh)
    public void onViewClicked() {
        if (!edCity.getText().toString().trim().isEmpty()) {
            cityName = edCity.getText().toString().trim();
            OkhttpUtil.send(UtilValue.URL+cityName+UtilValue.URLKEY, handler, 0);
        }
    }

    /*      折线图显示       */
    private void showLinechar() {
        int color[] = new int[]{Color.RED, Color.BLUE};
        if (max.size() == 0 || max == null) {
            return;
        }
        XAxis xAxis = linchart.getXAxis();
        YAxis left = linchart.getAxisLeft();
        YAxis right = linchart.getAxisRight();
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        xAxis.setDrawGridLines(false);
        left.setEnabled(false);
        right.setEnabled(false);
        LineData lineData = new LineData();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(lineBottom));
        for (int i = 0; i < list.size(); i++) {
            List<Entry> entries = new ArrayList<>();

            for (int j = 0; j < list.get(i).size(); j++) {
                entries.add(new Entry(j, list.get(i).get(j)));
            }
            LineDataSet lineDataSet = new LineDataSet(entries, "");
            lineDataSet.setCircleColor(color[i]);
            lineDataSet.setCircleHoleColor(color[i]);
            lineDataSet.setColor(color[i]);
            lineDataSet.setValueTextSize(10);
            int finalI = i;
            lineDataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (finalI == 0 ? "最高温:" + (int) value : "最低温" + (int) value);
                }
            });
            lineData.addDataSet(lineDataSet);
        }

        linchart.setData(lineData);
        linchart.setDescription(null);
        linchart.getLegend().setEnabled(false);
        linchart.setExtraOffsets(20, 0, 20, 0);
        linchart.invalidate();
    }

    /*      条形图显示       */
    private void showBarchart() {
        if (humi.size() == 0) {
            return;
        }
        XAxis xAxis = barchart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(barBottom1));
        BarData data = new BarData();
        for (int i = 0; i < list2.size(); i++) {
            List<BarEntry> entries = new ArrayList<>();

            for (int j = 0; j < list2.get(i).size(); j++) {
                entries.add(new BarEntry(i, list2.get(i).get(j)));
            }
            BarDataSet set = new BarDataSet(entries, "");

            set.setColor(color[i]);
            data.addDataSet(set);
            set.setValueTextSize(12);
        }

        barchart.setData(data);

        /* (barSpace＋ barWidth)*4 ＋ groupSpace  = 1   计算bar间隙公式*/
        data.setBarWidth(0.135f);
        barchart.groupBars(0.18f, 0.3f, 0);

        barchart.setDescription(null);
        YAxis axisLeft = barchart.getAxisLeft();
        axisLeft.setEnabled(false);
        YAxis axisRight = barchart.getAxisRight();
        axisLeft.setAxisMinimum(0);
        axisRight.setAxisMinimum(0);
        xAxis.setAxisMaximum(barBottom1.size());
        xAxis.setAxisMinimum(0);
        axisRight.setEnabled(false);
        Legend legend = barchart.getLegend();
        xAxis.setCenterAxisLabels(true);   //将label标签设置在中间
        /*legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextColor(Color.BLACK);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(20);*/
        legend.setEnabled(false);
        barchart.setExtraTopOffset(0);
        barchart.setDrawGridBackground(false);
        barchart.invalidate();
    }

    /*      显示头部信息      */
    private void showHead(List<WeatherValue.HeWeather6Bean.DailyForecastBean> list) {
        WeatherValue.HeWeather6Bean.DailyForecastBean forecastBean = list.get(0);
        int max = Integer.parseInt(forecastBean.getTmp_max());
        int min = Integer.parseInt(forecastBean.getTmp_min());
        int te = (max - min) / 2;
        temp.setText(te + "℃");
        feng.setText(forecastBean.getWind_dir() + "/" + forecastBean.getWind_sc() + "级");
        shidu.setText(forecastBean.getHum());
        time.setText(updatTime);
        weatherInfo.setText(forecastBean.getCond_txt_d());
        if (!forecastBean.getCond_code_d().isEmpty()) {
            int img = inttImage("i" + forecastBean.getCond_code_d());
            wtImg.setImageResource(img);
        }
        if (!edCity.getText().toString().trim().isEmpty()) {
            cityName = edCity.getText().toString().trim();
            city.setText(cityName);
        }
    }


    /*     反射机制获取drwable中资源照片       */
    private int inttImage(String imgName) {
        try {
            if (imgName.isEmpty()) {
                return 0;
            }
            Field field = R.drawable.class.getField(imgName);
            return field.getInt(imgName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
