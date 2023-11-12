package com.example.myapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.TimKiemNoiBac_Fragment;
import com.example.myapplication.TimKiemTrending_Fragment;
import com.example.myapplication.TimKiem_Fragment;

public class TimKiem_TabLayout_Adapter extends FragmentStateAdapter {

    //Dùng cho lớp FragmentActivity
//    public TimKiem_TabLayout_Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
//        super(fragmentManager, lifecycle);
//    }

    //Dùng cho lớp Fragment
    public TimKiem_TabLayout_Adapter(@NonNull Fragment fragment) {
        super(fragment.getChildFragmentManager(), fragment.getLifecycle());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1) {
            TimKiemNoiBac_Fragment noibac = new TimKiemNoiBac_Fragment();
            return noibac;
        }
        if(position == 2) {
            TimKiemTrending_Fragment trending = new TimKiemTrending_Fragment();
            return trending;
        }
        return new TimKiemNoiBac_Fragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
