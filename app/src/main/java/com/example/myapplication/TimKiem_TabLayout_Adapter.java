package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TimKiem_TabLayout_Adapter extends FragmentStateAdapter {
    public TimKiem_TabLayout_Adapter(@NonNull Fragment fragment) {
        super(fragment);
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
