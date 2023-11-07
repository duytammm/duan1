package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TimKiem_Fragment extends Fragment {

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
