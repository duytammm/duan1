package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.BaiHat_Adapter;
import com.example.myapplication.model.BaiHat;
import com.example.myapplication.model.PlayList_BaiHat;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CT_Playlist_AddSong extends AppCompatActivity {
    private Toolbar tbPL;
    private ImageView ivNhac;
    private CollapsingToolbarLayout collaPlayList;
    private TextView txtTopNhac;
    private FloatingActionButton floatAddBH;
    private RecyclerView recyclerviewDSNhac;
    private BaiHat_Adapter baiHatAdapter;
    private List<BaiHat> lstBaiHats = new ArrayList<>();

    private void initView() {
        floatAddBH = findViewById(R.id.floatAddBH);
        recyclerviewDSNhac = findViewById(R.id.recyclerviewDSNhac);
        tbPL = findViewById(R.id.tbPL);
        ivNhac = findViewById(R.id.ivNhac);
        txtTopNhac = findViewById(R.id.txtTopNhac);

        setSupportActionBar(tbPL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
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
        setContentView(R.layout.activity_ct_playlist_add_song);
        initView();
        floatAddBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CT_Playlist_AddSong.this, AllSong_Activity.class);
                startActivity(i);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("idPL", Context.MODE_PRIVATE);;
        String currentPL = sharedPreferences.getString("id","");
        String ten = sharedPreferences.getString("ten","");
        txtTopNhac.setText(ten);
        getSupportActionBar().setTitle(ten);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Bia_PlayList").child(currentPL);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                // Lay link anh
                String imageUrl = downloadUrl.toString();
                if (!isDestroyed()) {
                    // Load image with Glide here
                    Glide.with(getApplicationContext()).load(imageUrl).into(ivNhac);
                }

//                Glide.with(CT_Playlist_AddSong.this).load(imageUrl).error(R.drawable.avatar_null).into(ivNhac);

                // load len
//                if(!imageUrl.equals("")){
//                } else {
//                    Glide.with(CT_Playlist_AddSong.this).load(R.drawable.avatar_null).into(ivNhac);
//                }
            }
        });

        fillAdapter(currentPL);
    }

    private void fillAdapter(String idPLcurrent) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PLAYLIST_BAIHAT");
        reference.orderByChild("idpl").equalTo(idPLcurrent)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                PlayList_BaiHat playListBaiHat = dataSnapshot.getValue(PlayList_BaiHat.class);
                                if(playListBaiHat != null && playListBaiHat.getIdbh() > 0) {
                                    getBaiHatInfo(playListBaiHat.getIdbh());
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(CT_Playlist_AddSong.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(CT_Playlist_AddSong.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updateRecyclerView() {
        // Cập nhật RecyclerView
        baiHatAdapter = new BaiHat_Adapter(CT_Playlist_AddSong.this, lstBaiHats);
        LinearLayoutManager manager = new LinearLayoutManager(CT_Playlist_AddSong.this);
        recyclerviewDSNhac.setLayoutManager(manager);
        recyclerviewDSNhac.setAdapter(baiHatAdapter);
        baiHatAdapter.notifyDataSetChanged();
    }


}