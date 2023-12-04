package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.PLYT_Adapter;
import com.example.myapplication.model.BaiHat;
import com.example.myapplication.model.See;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PlayList_YeuThich_Activity extends AppCompatActivity {
    private Toolbar tbPLYT;
    private RecyclerView rcvYT;
    private List<BaiHat> lstBaiHats;
    private PLYT_Adapter adapter;
    private void initView() {
        tbPLYT = findViewById(R.id.tbPLYT);
        rcvYT = findViewById(R.id.rcvYT);

        setSupportActionBar(tbPLYT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle("Danh sách bài hát yêu thích");
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
        setContentView(R.layout.activity_play_list_yeu_thich);
        initView();

        lstBaiHats = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("DataUser",MODE_PRIVATE);
        String idUserCurrent = sharedPreferences.getString("id","");

        getDataAndDisplay(idUserCurrent);
    }

    private void getDataAndDisplay(String idUserCurrent) {
        DatabaseReference seeTableReference = FirebaseDatabase.getInstance().getReference("SEE");

        seeTableReference.orderByChild("idUser").equalTo(idUserCurrent)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot seeSnapshot : snapshot.getChildren()) {
                            See seeModel = seeSnapshot.getValue(See.class);
                            if (seeModel != null && seeModel.getIdBH() > 0) {
                                getBaiHatInfo(seeModel.getIdBH());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PlayList_YeuThich_Activity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getBaiHatInfo(int idBH) {
        DatabaseReference baiHatReference = FirebaseDatabase.getInstance().getReference("BAIHAT");

        baiHatReference.child(String.valueOf(idBH))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot baiHatSnapshot) {
                        BaiHat baiHatModel = baiHatSnapshot.getValue(BaiHat.class);
                        if (baiHatModel != null) {
                            lstBaiHats.add(baiHatModel);
                            updateRecyclerView();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PlayList_YeuThich_Activity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateRecyclerView() {
        adapter = new PLYT_Adapter(PlayList_YeuThich_Activity.this,lstBaiHats);
        LinearLayoutManager manager = new LinearLayoutManager(PlayList_YeuThich_Activity.this);
        rcvYT.setLayoutManager(manager);
        rcvYT.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}