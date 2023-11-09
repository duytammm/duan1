package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CT_PlayList_fragment extends Fragment {
    private RecyclerView recyclerviewDSNhac;
    private FloatingActionButton floataDS;
    private CollapsingToolbarLayout collaPlayList;
    private ImageView ivNhac;
    private TextView txtTopNhac;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chitietplaylist_fragment,container,false);
        recyclerviewDSNhac = v.findViewById(R.id.recyclerviewDSNhac);
        floataDS = v.findViewById(R.id.floatDS);
        collaPlayList = v.findViewById(R.id.collaPlayList);
        ivNhac = v.findViewById(R.id.ivNhac);
        txtTopNhac = v.findViewById(R.id.txtTopNhac);

        return v;
    }
}
