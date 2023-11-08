package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TK_NgheSi_Fragment extends Fragment {
    private ImageView imgTKNgheSi;
    private LinearLayout linearXem, linearThem, linearHistory, linearSua, linearLogout, linearSetting;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.taikhoannghesi_fragment,container,false);
        imgTKNgheSi = v.findViewById(R.id.imgTKNgheSi);
        linearXem = v.findViewById(R.id.linearXem);
        linearThem = v.findViewById(R.id.linearThem);
        linearHistory = v.findViewById(R.id.linearHistory);
        linearSua = v.findViewById(R.id.linearSua);
        linearLogout = v.findViewById(R.id.linearLogout);
        linearSetting = v.findViewById(R.id.linearSetting);
        return v;
    }
}
