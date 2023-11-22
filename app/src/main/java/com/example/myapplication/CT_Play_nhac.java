package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adapter.ViewPager_DiaNhac_Adapter;

import java.util.ArrayList;
import java.util.List;

public class CT_Play_nhac extends AppCompatActivity {
    Toolbar tb;
    ViewPager vpgPlayNhac;
    TextView timeSong, totaltimeSong;
    SeekBar seekbarSong;
    ImageButton ngaunhien, imgBack, imgPlay, imgNext, imgrepeat;

    private void initView() {
        tb = findViewById(R.id.tb);
        vpgPlayNhac = findViewById(R.id.vpgPlayNhac);
        timeSong = findViewById(R.id.timeSong);
        totaltimeSong = findViewById(R.id.totaltimeSong);
        seekbarSong = findViewById(R.id.seekbarSong);
        ngaunhien = findViewById(R.id.ngaunhien);
        imgBack = findViewById(R.id.imgBack);
        imgPlay = findViewById(R.id.imgPlay);
        imgNext = findViewById(R.id.imgNext);
        imgrepeat = findViewById(R.id.imgrepeat);

        setSupportActionBar(tb);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        tb.setTitleTextColor(Color.WHITE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct_play_nhac);
        initView();

        List<View> views = new ArrayList<>();
        View view1 = LayoutInflater.from(this).inflate(R.layout.dianhac_fragment, null);
        views.add(view1);
        ViewPager_DiaNhac_Adapter adapter = new ViewPager_DiaNhac_Adapter(views);
        vpgPlayNhac.setAdapter(adapter);

    }
}