package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.BaiHat_Adapter;
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

public class XemBH_Activity extends AppCompatActivity {
    private BaiHat_Adapter baiHatAdapter;
    private List<BaiHat> lstBaiHats = new ArrayList<>();
    private Toolbar tb;
    private RecyclerView rcvLstMusic;
    private void initView() {
        tb = findViewById(R.id.tb);
        rcvLstMusic = findViewById(R.id.rcvLstMusic);
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
        setContentView(R.layout.activity_xem_bh);
        initView();

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle("Danh sách bài hát của tôi");

        SharedPreferences sharedPreferences = getSharedPreferences("DataID",MODE_PRIVATE);
        String idUserCurrent = sharedPreferences.getString("id","");

        getDataAndDisplay(idUserCurrent);
    }

//    private void getDataAndDisplay(String idUserCurrent) {
//        DatabaseReference seeTableReference = FirebaseDatabase.getInstance().getReference("SEE");
//
//        // Truy vấn dữ liệu từ bảng thứ ba
//        seeTableReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot seeSnapshot : snapshot.getChildren()) {
//                    See seeModel = seeSnapshot.getValue(See.class);
//
//                    // Kiểm tra nếu seeModel không null
//                    if (seeModel != null) {
//                        // Kiểm tra xem có người dùng và bài hát tương ứng không
//                        if (seeModel.getIdUser() != null && seeModel.getIdBH() > 0 && idUserCurrent.equals(seeModel.getIdUser())) {
//                            // Gọi phương thức để lấy thông tin người dùng và bài hát
//                            getUserAndBaiHatInfo(seeModel);
//                        }
//                    }
//                }
//
//                // Cập nhật RecyclerView sau khi đã kiểm tra hết danh sách
//                updateRecyclerView();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(XemBH_Activity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
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
                        Toast.makeText(XemBH_Activity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void getUserAndBaiHatInfo(See seeModel) {
//        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("USER");
//        DatabaseReference baiHatReference = FirebaseDatabase.getInstance().getReference("BAIHAT");
//
//        // Truy vấn thông tin người dùng từ bảng user
//        userReference.child(seeModel.getIdUser()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
//                User user = userSnapshot.getValue(User.class);
//
//                // Truy vấn thông tin bài hát từ bảng baihat
//                baiHatReference.child(String.valueOf(seeModel.getIdBH())).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot baiHatSnapshot) {
//                        BaiHat baiHatModel = baiHatSnapshot.getValue(BaiHat.class);
//
//                        // Kiểm tra nếu user và baiHatModel không null
//                        if (user != null && baiHatModel != null) {
//                            // Thêm bài hát vào danh sách
//                            lstBaiHats.add(baiHatModel);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(XemBH_Activity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(XemBH_Activity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

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
                        Toast.makeText(XemBH_Activity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updateRecyclerView() {
        // Cập nhật RecyclerView
        baiHatAdapter = new BaiHat_Adapter(XemBH_Activity.this, lstBaiHats);
        LinearLayoutManager manager = new LinearLayoutManager(XemBH_Activity.this);
        rcvLstMusic.setLayoutManager(manager);
        rcvLstMusic.setAdapter(baiHatAdapter);
        baiHatAdapter.notifyDataSetChanged();
    }
}