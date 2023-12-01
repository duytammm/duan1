package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.TK_PlayList_Adapter;
import com.example.myapplication.DAO.PlayList_DAO;
import com.example.myapplication.model.PlayList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuanLyBH_Activity extends AppCompatActivity {
    private Toolbar tbQLBH;
    private RecyclerView rcvToTalPL;
    private FloatingActionButton fabAddPL;
    private PlayList_DAO pldao;
    private TK_PlayList_Adapter adapter;
    private void initView() {
        tbQLBH = findViewById(R.id.tbQLBH);
        rcvToTalPL = findViewById(R.id.rcvToTalPL);
        fabAddPL = findViewById(R.id.fabAddPL);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Thực hiện hành động quay về trang trước đó
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_bh);
        initView();

        setSupportActionBar(tbQLBH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle("Quản Lý Bài Hát");

        pldao = new PlayList_DAO(this);
        fillData();
    }

    private void fillData() {
        pldao.getPlayList(new PlayList_DAO.getListPlayList() {
            @Override
            public void ongetSuccess(ArrayList<PlayList> ListPlayList) {
                GridLayoutManager manager = new GridLayoutManager(QuanLyBH_Activity.this,3);
                adapter = new TK_PlayList_Adapter(ListPlayList,QuanLyBH_Activity.this);
                rcvToTalPL.setLayoutManager(manager);
                rcvToTalPL.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }
}