package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DAO.User_DAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DangKyActivity extends AppCompatActivity {
    private Button btDK, btTV;
    private EditText edtMail, edtMatKhau, edNLMatKhau;
    private ProgressDialog dialog;
    private User_DAO dao;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private void initView() {
        btDK = findViewById(R.id.btDangKy);
        btTV = findViewById(R.id.btTroVe);
        edtMail = findViewById(R.id.edtMail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edNLMatKhau = findViewById(R.id.edtNLMatKhau);
    }
    private void clickListener(){
        btDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        btTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        initView();
        clickListener();

        dialog = new ProgressDialog(this);
        dialog.setTitle("Alert");
        dialog.setMessage("Đang tải. Vui lòng chờ trong giây lát...");
        dao = new User_DAO(this);

    }

    private void signUp() {
        dialog.show();
        String mail = edtMail.getText().toString();
        String pass = edtMatKhau.getText().toString();
        String rePass = edNLMatKhau.getText().toString();

        if (mail.equals("") || pass.equals("") || rePass.equals("")) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }
        if (!pass.equals(rePass)) {
            Toast.makeText(this, "Vui lòng nhập 2 pass trùng nhau!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }
        if (pass.length() < 6 && rePass.length() < 6) {
            Toast.makeText(this, "Mật khẩu ít nhất 6 kí tự!!!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(this, "Vui lòng nhập mail theo định dạng: abc@....", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }

        DatabaseReference userListRef = FirebaseDatabase.getInstance().getReference("USER");
        userListRef.orderByChild("email").equalTo(mail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dialog.dismiss();
                if (snapshot.exists()) {
                    // Email is already registered
                    Toast.makeText(DangKyActivity.this, "Email đã tồn tại. Vui lòng chọn email khác!", Toast.LENGTH_SHORT).show();
                } else {
                    // Email is not registered, proceed with the sign-up
                    dao.registerUser(mail, pass);
                    startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
                    finishAffinity();
                    Toast.makeText(DangKyActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(DangKyActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

