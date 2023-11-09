package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myapplication.Adapter.SlideShow_Adapter;
import com.example.myapplication.model.SlideShow;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    //Slide show sử dụng sự hỗ trợ của hai thư viện bên ngoài: CircleIndicator và glide của git
    private ViewPager viewPager;
    private CircleIndicator cirCle;
    private List<SlideShow> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        cirCle = findViewById(R.id.cirCle);

        SlideShow_Adapter adapter = new SlideShow_Adapter(MainActivity.this,lst());
        viewPager.setAdapter(adapter);
        cirCle.setViewPager(viewPager);
        adapter.registerDataSetObserver(cirCle.getDataSetObserver());
    }

    private List<SlideShow> lst() {
        lst = new ArrayList<>();
        lst.add(new SlideShow(R.drawable.trangchu_banner1));
        lst.add(new SlideShow(R.drawable.trangchu_banner2));
        lst.add(new SlideShow(R.drawable.trangchu_banner3));
        lst.add(new SlideShow(R.drawable.trangchu_banner4));
        lst.add(new SlideShow(R.drawable.trangchu_banner5));
        return lst;
    }
}