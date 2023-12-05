package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.HoaDon_Adapter;
import com.example.myapplication.DAO.HoaDon_DAO;
import com.example.myapplication.model.HoaDon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuanLyHD_Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rcv;
    private HoaDon_DAO hoaDonDao;
    private HoaDon_Adapter hoaDonAdapter;

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        rcv = findViewById(R.id.rcv);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle("Quản lý hóa đơn");
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
        setContentView(R.layout.activity_quan_ly_hd);
        initView();

        hoaDonDao = new HoaDon_DAO(this);

        fillAdapter();
    }

    private void fillAdapter() {
        hoaDonDao.getHD(new HoaDon_DAO.getAllHD() {
            @Override
            public void ongetSuccess(ArrayList<HoaDon> getHD) {
                hoaDonAdapter = new HoaDon_Adapter(getHD,QuanLyHD_Activity.this);
                LinearLayoutManager manager = new LinearLayoutManager(QuanLyHD_Activity.this);
                rcv.setLayoutManager(manager);
                rcv.setAdapter(hoaDonAdapter);
                hoaDonAdapter.notifyDataSetChanged();
            }
        });
    }
}