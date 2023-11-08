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
import androidx.recyclerview.widget.RecyclerView;

public class ThuVien_Fragment extends Fragment {

    private ImageView imgNgheGanDay, imgLoc;
    private RecyclerView rcvNgheSi;
    private LinearLayout linearThemNS, linearThemPodcast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.thuvien_fragment,container,false);
        imgNgheGanDay = v.findViewById(R.id.imgNgheGanDay);
        imgLoc = v.findViewById(R.id.imgLoc);
        rcvNgheSi = v.findViewById(R.id.rcvNgheSi);
        linearThemNS = v.findViewById(R.id.linearThemNS);
        linearThemPodcast = v.findViewById(R.id.linearThemPodcast);

        return v;
    }
}
