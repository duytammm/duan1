package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity_QuanLy extends AppCompatActivity {
    private FrameLayout framLayout;
    private BottomNavigationView bottomNagigatiom;
    private Toolbar toolbar;
    private RelativeLayout constrain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_ql);
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
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framLayout, new TK_QuanLy_fragment())
                            .commit();
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

//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.framLayout, new TK_QuanLy_fragment())
//                            .commit();
//                    int role = getCurrentUserRole();
//                    handleRoleSelection(role);
                }
                toolbar.setTitle(item.getTitle());
            }
        });
    }

    private void getCurrentUserRole(OnRoleReceivedListener listener) {
        SharedPreferences inforSharedPreferences = getSharedPreferences("DataUser", MODE_PRIVATE);
        String mail = inforSharedPreferences.getString("email", "");
        String Mail = replace(mail);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference("USER");
        DatabaseReference currentUserReference = userReference.child(Mail);

        currentUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int role = 0;
                if (dataSnapshot.exists()) {
                    role = dataSnapshot.child("role").getValue(Integer.class);
                }
                listener.onRoleReceived(role);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi khi truy vấn bị hủy
                Toast.makeText(MainActivity_QuanLy.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    interface OnRoleReceivedListener {
        void onRoleReceived(int role);
    }

    private void handleRoleSelection(int role) {
        switch(role){
            case 1:{
                Toast.makeText(MainActivity_QuanLy.this, "Quan tri vien", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framLayout, new TK_NguoiDung_fragment())
                        .commit();
                break;
            }
            case 2:{
                Toast.makeText(MainActivity_QuanLy.this, "Nghe si", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framLayout, new TK_NgheSi_Fragment())
                        .commit();
                break;
            }
            case 3:{
                Toast.makeText(MainActivity_QuanLy.this, "NguoiDung", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framLayout, new TK_QuanLy_fragment())
                        .commit();
                break;
            }
        }
    }

//    private int getCurrentUserRole() {
//        SharedPreferences inforSharedPreferences = getSharedPreferences("DataUser",MODE_PRIVATE);
//        String mail = inforSharedPreferences.getString("email","");
//        String Mail = replace(mail);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference userReference = database.getReference("USER");
//        DatabaseReference currentUserReference = userReference.child(Mail);
//        final int[] role = {0};
//        currentUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    role[0] = dataSnapshot.child("role").getValue(Integer.class);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Xử lý lỗi khi truy vấn bị hủy
//                Toast.makeText(MainActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        return role[0];
//    }

    public String replace(String email) {
        // Thay thế các ký tự không hợp lệ trong email
        return email.replace('.', '_')
                .replace('#', '_')
                .replace('$', '_')
                .replace('[', '_')
                .replace(']', '_');
    }

}