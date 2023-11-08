package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.Adapter.TimKiem_TabLayout_Adapter;
import com.google.android.material.search.SearchView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TimKiem_Fragment extends Fragment {
    private SearchView svTimKiem;
    private ImageView imgLofi,imgPodcast, imgMelody;
    private TabLayout tabLayout;
    private ViewPager2 vpg2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timkiem_fragment,container,false);
        svTimKiem = view.findViewById(R.id.svTimKiem);
        imgMelody = view.findViewById(R.id.imgMelody);
        imgLofi = view.findViewById(R.id.imgLofi);
        imgPodcast = view.findViewById(R.id.imgPodcast);

        tabLayout = view.findViewById(R.id.tablayout);
        vpg2 = view.findViewById(R.id.vpg2);

        TimKiem_TabLayout_Adapter adapter = new TimKiem_TabLayout_Adapter(TimKiem_Fragment.this);
        vpg2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, vpg2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0:{
                        tab.setText("Nổi bật");
                        break;
                    }
                    case 1:{
                        tab.setText("Trending Music");
                        break;
                    }
                }
            }
        }).attach();

        return view;
    }
}
