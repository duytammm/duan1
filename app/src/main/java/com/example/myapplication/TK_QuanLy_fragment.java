package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class TK_QuanLy_fragment extends Fragment {
    private CardView cvQLTaiKhoan, cvTaoTK, cvQLBH, cvQLHD, cvSetting, cvDX;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.taikhoanquanly_fragment,container,false);
        cvQLTaiKhoan = v.findViewById(R.id.cvQLTaiKhoan);
        cvTaoTK = v.findViewById(R.id.cvTaoTK);
        cvQLBH = v.findViewById(R.id.cvQLBH);
        cvQLHD = v.findViewById(R.id.cvQLHD);
        cvSetting = v.findViewById(R.id.cvSetting);
        cvDX = v.findViewById(R.id.cvDX);

        return v;
    }
}
