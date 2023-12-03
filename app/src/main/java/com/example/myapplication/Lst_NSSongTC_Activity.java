package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.BaiHat_Adapter;
import com.example.myapplication.model.BaiHat;
import com.example.myapplication.model.See;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Lst_NSSongTC_Activity extends AppCompatActivity {
    private BaiHat_Adapter baiHatAdapter;
    private List<BaiHat> lstBaiHats = new ArrayList<>();
    private Toolbar tbPL;
    private CollapsingToolbarLayout collaPlayList;
    private TextView txtTopNhac;
    private ImageView ivNhac;
    private RecyclerView recyclerviewDSNhac;
    private void initView() {
        tbPL = findViewById(R.id.tbPL);
        recyclerviewDSNhac = findViewById(R.id.recyclerviewDSNhac);
        collaPlayList = findViewById(R.id.collaPlayList);
        txtTopNhac = findViewById(R.id.txtTopNhac);
        ivNhac = findViewById(R.id.ivNhac);
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
        setContentView(R.layout.activity_lst_song_tc);
        initView();

        setSupportActionBar(tbPL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle("Danh sách bài hát");

        SharedPreferences sharedPreferences = getSharedPreferences("idNSTC", Context.MODE_PRIVATE);
        String idNScurent = sharedPreferences.getString("id","");
        String ten = sharedPreferences.getString("ten","");
        txtTopNhac.setText(ten);
//        String anh = sharedPreferences.getString("anh", "");
//        // Kiểm tra xem có đường dẫn ảnh hay không
//        if (!TextUtils.isEmpty(anh)) {
//            File file = new File(anh);
//            if (file.exists()) {
//                // Tạo Bitmap từ đường dẫn ảnh
//                Bitmap bitmap = BitmapFactory.decodeFile(anh);
//
//                // Set Bitmap làm background cho CollapsingToolbarLayout
//                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
//                collaPlayList.setBackground(drawable);
////                if (ivNhac != null) {
////                    ivNhac.setImageDrawable(drawable);
////                }
//            } else {
//
//            }
//        }

        getDataAndDisplay(idNScurent);
        getAnh(idNScurent);
    }

    private void getAnh(String idNScurent) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Avatar_User");
        storageReference.child(idNScurent).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Lay link anh
                String imageUrl = uri.toString();
                // load len
                Glide.with(Lst_NSSongTC_Activity.this).load(imageUrl).error(R.drawable.avatar_null).into(ivNhac);
                if (!TextUtils.isEmpty(imageUrl)) {
                    File file = new File(imageUrl);
                    if (file.exists()) {
                        // Tạo Bitmap từ đường dẫn ảnh
                        Bitmap bitmap = BitmapFactory.decodeFile(imageUrl);

                        // Set Bitmap làm background cho CollapsingToolbarLayout
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        collaPlayList.setBackground(drawable);
                    }
                }
            }
        });
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
                        Toast.makeText(Lst_NSSongTC_Activity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Lst_NSSongTC_Activity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updateRecyclerView() {
        // Cập nhật RecyclerView
        baiHatAdapter = new BaiHat_Adapter(Lst_NSSongTC_Activity.this, lstBaiHats);
        LinearLayoutManager manager = new LinearLayoutManager(Lst_NSSongTC_Activity.this);
        recyclerviewDSNhac.setLayoutManager(manager);
        recyclerviewDSNhac.setAdapter(baiHatAdapter);
        baiHatAdapter.notifyDataSetChanged();
    }
}