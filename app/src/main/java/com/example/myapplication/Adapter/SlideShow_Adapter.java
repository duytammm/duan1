package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.SlideShow;

import java.util.ArrayList;
import java.util.List;

public class SlideShow_Adapter extends PagerAdapter {
    private ImageView imgBanner;
    private Context c;
    private List<SlideShow> lst = new ArrayList<>();

    public SlideShow_Adapter(Context c, List<SlideShow> lst) {
        this.c = c;
        this.lst = lst;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.main_item_slideshow,container,false);
        imgBanner = view.findViewById(R.id.imgBanner);

        SlideShow slideShow = lst.get(position);
        if(slideShow != null) {
            Glide.with(c).load(slideShow.getResourceId()).into(imgBanner);
        }
        //ADD VIEW VÀO VIEWGROUP
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if(lst != null) {
            return lst.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
