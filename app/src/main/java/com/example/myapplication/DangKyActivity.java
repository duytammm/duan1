package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DangKyActivity extends AppCompatActivity {

    private EditText edtTenDK, edtMatKhau, edNLMatKhau;
    ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        Button btDK = findViewById(R.id.btDangKy);
        Button btTV = findViewById(R.id.btTroVe);
        edtTenDK = findViewById(R.id.edtTenDK);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edNLMatKhau = findViewById(R.id.edtNLMatKhau);
        progressbar = findViewById(R.id.progressbar);

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

    private void signUp() {
        progressbar.setVisibility(View.VISIBLE);
        mAuth=FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("USER");

        String email = edtTenDK.getText().toString().trim();
        String password = edtMatKhau.getText().toString().trim();
        String repassword = edNLMatKhau.getText().toString().trim();
        //Tao userID cho moi nguoi dung duoc them vao
        String userID = reference.push().getKey();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(DangKyActivity.this, "Nhap username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(DangKyActivity.this, "Nhap password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(repassword)){
            Toast.makeText(DangKyActivity.this, "Nhap lai password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(repassword)) {
            Toast.makeText(this, "Pass khong trung nhau, vui long nhap lai", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 6 || repassword.length() < 6) {
            Toast.makeText(this, "Vui long nhap mat khau it nhat 6 kitu bao gom chu va so", Toast.LENGTH_SHORT).show();
        }

        if(!email.equals("") && !password.equals("") &&  !repassword.equals("") && password.equals(repassword)) {
            User user = new User(email,password);
            reference.child(userID).setValue(user);

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressbar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(DangKyActivity.this, "Dang ky thanh cong",
                                        Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(DangKyActivity.this, DangNhapActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(DangKyActivity.this, "Dang ky that bai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Dang ky that bai", Toast.LENGTH_SHORT).show();
        }
    }
}