package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PlayList_Activity extends AppCompatActivity {
    private RecyclerView recyclerviewDSNhac;
    private FloatingActionButton floataDS;
    private CollapsingToolbarLayout collaPlayList;
    private ImageView ivNhac;
    private TextView txtTopNhac;
    private void initView() {
        recyclerviewDSNhac = findViewById(R.id.recyclerviewDSNhac);
        floataDS = findViewById(R.id.floatDS);
        collaPlayList = findViewById(R.id.collaPlayList);
        ivNhac = findViewById(R.id.ivNhac);
        txtTopNhac = findViewById(R.id.txtTopNhac);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
        initView();

    }
}