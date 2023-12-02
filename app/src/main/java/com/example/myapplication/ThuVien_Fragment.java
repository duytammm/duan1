package com.example.myapplication;

import android.content.Intent;
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

import com.example.myapplication.model.BaiHat;

import java.util.List;

public class ThuVien_Fragment extends Fragment {

    private ImageView imgNgheGanDay, imgLoc;
    private RecyclerView rcvNgheSi;
    private LinearLayout linearThemNS, linearThemPodcast;
    private List<BaiHat> lstBH;

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

        clickListener();

        return v;
    }

    private void clickListener() {
        linearThemNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ListNS_Activity.class));
            }
        });
    }

}
