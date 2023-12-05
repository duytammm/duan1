package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.AllSong_Adapter;
import com.example.myapplication.DAO.BaiHat_DAO;
import com.example.myapplication.model.BaiHat;

import java.util.List;

public class AllSong_Activity extends AppCompatActivity {
    private Toolbar tbsearchSong;
    private RecyclerView rcvSong;
    private BaiHat_DAO baiHatDao;
    private AllSong_Adapter allSongAdapter;
    private void initView() {
        tbsearchSong = findViewById(R.id.tbsearchSong);
        rcvSong = findViewById(R.id.rcvSong);
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
        setContentView(R.layout.activity_all_song);
        initView();

        setSupportActionBar(tbsearchSong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_black);
        getSupportActionBar().setTitle("Chọn bài hát cần thêm vào playlist");

        baiHatDao = new BaiHat_DAO(this);
        fillAdapter();
    }

    private void fillAdapter() {
        baiHatDao.getAllSong(new BaiHat_DAO.getLstBH() {
            @Override
            public void onSuccess(List<BaiHat> LstbaiHats) {
                allSongAdapter = new AllSong_Adapter(AllSong_Activity.this,LstbaiHats);
                LinearLayoutManager manager = new LinearLayoutManager(AllSong_Activity.this);
                rcvSong.setLayoutManager(manager);
                rcvSong.setAdapter(allSongAdapter);
                allSongAdapter.notifyDataSetChanged();
            }
        });
    }
}