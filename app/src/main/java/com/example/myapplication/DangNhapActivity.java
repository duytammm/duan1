package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DAO.User_DAO;
import com.example.myapplication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DangNhapActivity extends AppCompatActivity {
    User_DAO dao;
    private EditText edtTenDN, edtMatKhau;
    private ProgressDialog dialog;
    SharedPreferences userPreferences;
    FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        Button btDN = findViewById(R.id.btDangNhap);
        Button btDK = findViewById(R.id.btDangKy);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtTenDN = findViewById(R.id.edtTenDN);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Vui lòng chờ giây lát...");
        mAuth = FirebaseAuth.getInstance();
        dao = new User_DAO(this);

        btDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAccount();
            }
        });

        btDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));
            }
        });
    }

    private void DN(String email, String password) {
        DatabaseReference userListRef = FirebaseDatabase.getInstance().getReference("USER");
        userListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.dismiss();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if(user.getEmail().equals(email) && user.getMatKhau().equals(password)){
                            Integer trangThaiInteger = user.getTrangThai();
                            Integer roleInteger = user.getRole();
                            String id = user.getIdUser();
                            String hoten = user.getHotenUser();
                            if (trangThaiInteger != null) {
                                int trangThai = trangThaiInteger.intValue();
//                                Toast.makeText(DangNhapActivity.this, "Trạng thái: " + trangThai, Toast.LENGTH_SHORT).show();
                                if(trangThai == 1 ){
                                    if(roleInteger != null) {
                                        int role = roleInteger.intValue();
                                        switch (role){
                                            case 1: {
                                                startActivity(new Intent(DangNhapActivity.this, MainActivity_NguoiDung.class));
                                                finishAffinity();
                                                Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công!!!", Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                            case 2: {
                                                SharedPreferences sharedPreferences = getSharedPreferences("DataID",MODE_PRIVATE);
                                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                                edit.putString("id",id);
                                                edit.putString("hoten",hoten);
                                                edit.apply();
                                                startActivity(new Intent(DangNhapActivity.this, MainActivity_NgheSi.class));
                                                finishAffinity();
                                                Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công!!!", Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                            case 3: {
                                                startActivity(new Intent(DangNhapActivity.this, MainActivity_QuanLy.class));
                                                finishAffinity();
                                                Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công!!!", Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                        }
                                    }


                                }
                                if(trangThai == 0) {
                                    Toast.makeText(DangNhapActivity.this, "Tài khoản bị khóa tạm thời do vi phạm nội quy cộng đồng", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (trangThai == -1) {
                                    Toast.makeText(DangNhapActivity.this, "Tài khoản bị khóa. Vui lòng liên hệ quản trị viên!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }

                    }
                } else {
                    Toast.makeText(DangNhapActivity.this, "Tài khoản không tồn tại. Vui lòng đăng ký một tài khoản mới!!!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xảy ra lỗi khi truy vấn database
            }
        });
    }

    private void checkAccount() {
        dialog.setTitle("Thông báo");
        dialog.setMessage("Đang đăng nhập...");
        dialog.show();
        String email = edtTenDN.getText().toString().trim();
        String password = edtMatKhau.getText().toString().trim();

        DatabaseReference userListRef = FirebaseDatabase.getInstance().getReference("USER");
        userListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dialog.dismiss();
                if(snapshot.exists()) {
                    boolean isUserFound = false;
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null && user.getEmail() != null && user.getMatKhau() != null) {
                            String mail = user.getEmail();
                            String pass = user.getMatKhau();
                            String id = user.getIdUser();
                            if(mail.equals(email) && pass.equals(password)) {
                                isUserFound = true;
                                userPreferences = getSharedPreferences("DataUser",MODE_PRIVATE);
                                SharedPreferences.Editor edtUser = userPreferences.edit();
                                edtUser.putString("email",email);
                                edtUser.putString("id",id);
                                edtUser.apply();
                                DN(email,password);
                                return;
                            }
                        }
                    }
                    if (!isUserFound) {
                        Toast.makeText(DangNhapActivity.this, "Sai mật khẩu hoặc email. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DangNhapActivity.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DangNhapActivity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }
}