package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    private FrameLayout framLayout;
    private BottomNavigationView bottomNagigatiom;
    private Toolbar toolbar;
    private RelativeLayout constrain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
        bottomNagigatiom = findViewById(R.id.bottomNagigatiom);
        framLayout = findViewById(R.id.framLayout);
        constrain = findViewById(R.id.constrain);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trang chủ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_music);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framLayout,new Main_Home())
                .commit();

        bottomNagigatiom.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framLayout, new Main_Home())
                            .commit();
                }
                if (item.getItemId() == R.id.search) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framLayout, new TimKiem_Fragment())
                            .commit();
                }
                if (item.getItemId() == R.id.library) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framLayout, new ThuVien_Fragment())
                            .commit();
                }
                if (item.getItemId() == R.id.account) {
//                    //Lay user hien tai
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    String userId = user.getUid();
//
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference rolesRef = database.getReference("USER");
//                    rolesRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                int role = dataSnapshot.child("Role").getValue(int.class);
//
//                                // Sử dụng vai trò của người dùng
//                                switch(role){
//                                    case 1:{
//                                        Toast.makeText(MainActivity.this, "Quan tri vien", Toast.LENGTH_SHORT).show();
//                                        getSupportFragmentManager().beginTransaction()
//                                            .replace(R.id.framLayout, new TK_QuanLy_fragment())
//                                            .commit();
//                                        break;
//                                    }
//                                    case 2:{
//                                        Toast.makeText(MainActivity.this, "Nghe si", Toast.LENGTH_SHORT).show();
//                                        getSupportFragmentManager().beginTransaction()
//                                                .replace(R.id.framLayout, new TK_NgheSi_Fragment())
//                                                .commit();
//                                        break;
//                                    }
//                                    case 3:{
//                                        Toast.makeText(MainActivity.this, "NguoiDung", Toast.LENGTH_SHORT).show();
//                                        getSupportFragmentManager().beginTransaction()
//                                                .replace(R.id.framLayout, new TK_NguoiDung_fragment())
//                                                .commit();
//                                        break;
//                                    }
//                                }
//                            } else {
//                                // Người dùng không có vai trò được xác định
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            // Xử lý lỗi khi truy vấn bị hủy
//                            Toast.makeText(MainActivity.this, "Error: " + databaseError, Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framLayout, new TK_NgheSi_Fragment())
                            .commit();

                }
                toolbar.setTitle(item.getTitle());
            }
        });
    }
}