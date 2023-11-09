package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Adapter.SlideShow_Adapter;
import com.example.myapplication.model.SlideShow;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    private LinearLayout phoXaXonXao, CaFe, playlist1, playlist2;
    private ImageView imgplaylist1, imgplaylist2;
    private TextView tv1, tv2;
    BottomNavigationView bottomNagigatiom
    private Toolbar toolbar;

    //Slide show sử dụng sự hỗ trợ của hai thư viện bên ngoài: CircleIndicator và glide của git
    private ViewPager viewPager;
    private CircleIndicator cirCle;
    private List<SlideShow> lst;

    private void anhXaView() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        cirCle = findViewById(R.id.cirCle);
        phoXaXonXao = findViewById(R.id.phoXaXonXao);
        CaFe = findViewById(R.id.CaFe);
        playlist1 = findViewById(R.id.playlist1);
        playlist2 = findViewById(R.id.playlist2);
        imgplaylist1 = findViewById(R.id.imgplaylist1);
        imgplaylist2 = findViewById(R.id.imgplaylist2);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        bottomNagigatiom = findViewById(R.id.bottomNagigatiom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        anhXaView();

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