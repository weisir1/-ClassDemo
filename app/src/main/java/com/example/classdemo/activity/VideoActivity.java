package com.example.classdemo.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.classdemo.BaseActivity;
import com.example.classdemo.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends BaseActivity {

    @BindView(R.id.video)
    SurfaceView video;
    private String url;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder holder;

    @Override
    public int getlayout() {
        return R.layout.videoactivity;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        url = extras.getString("path", null);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   //设置为半透明状态

        if (url != null) {
            settings();
        } else {
            Toast.makeText(this, "视频播放失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void settings() {
        mediaPlayer = new MediaPlayer();
        holder = video.getHolder();
        //直接显示到画面  不维持他自身的缓存
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.setKeepScreenOn(true);   //保持屏幕开启


        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


                    mediaPlayer.setDataSource(url);
                    mediaPlayer.setDisplay(holder);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });
    }


}
