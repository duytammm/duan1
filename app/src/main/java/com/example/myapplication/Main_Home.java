package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adapter.SlideShow_Adapter;
import com.example.myapplication.model.SlideShow;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class Main_Home extends Fragment {
    private LinearLayout phoXaXonXao, CaFe, playlist1, playlist2;
    private ImageView imgplaylist1, imgplaylist2;
    private TextView tv1, tv2;
    //Slide show sử dụng sự hỗ trợ của hai thư viện bên ngoài: CircleIndicator và glide của git
    private ViewPager viewPager;
    private CircleIndicator cirCle;
    private List<SlideShow> lst;
    private final int AUTO_SCROLL_DELAY = 3000; // Thời gian chuyển đổi giữa các trang (3 giây)
    private Handler handler;
    private Runnable runnable;

    @Override
    public void onResume() {
        super.onResume();
        startAutoSlider();
    }

    @Override
    public void onPause() {
        stopAutoSlider();
        super.onPause();
    }

    private void startAutoSlider() {
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItems = viewPager.getAdapter() != null ? viewPager.getAdapter().getCount() : 0;
                if (currentItem < totalItems - 1) {
                    viewPager.setCurrentItem(currentItem + 1);
                } else {
                    viewPager.setCurrentItem(0);
                }
                handler.postDelayed(this, AUTO_SCROLL_DELAY);
            }
        };
        handler.postDelayed(runnable, AUTO_SCROLL_DELAY);
    }

    private void stopAutoSlider() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_home,container,false);
        viewPager = view.findViewById(R.id.viewPager);
        cirCle = view.findViewById(R.id.cirCle);
        phoXaXonXao = view.findViewById(R.id.phoXaXonXao);
        CaFe = view.findViewById(R.id.CaFe);
        playlist1 = view.findViewById(R.id.playlist1);
        playlist2 = view.findViewById(R.id.playlist2);
        imgplaylist1 = view.findViewById(R.id.imgplaylist1);
        imgplaylist2 = view.findViewById(R.id.imgplaylist2);
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);

        SlideShow_Adapter adapter = new SlideShow_Adapter(getContext(),lst());
        viewPager.setAdapter(adapter);
        cirCle.setViewPager(viewPager);
        adapter.registerDataSetObserver(cirCle.getDataSetObserver());

        return view;
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
