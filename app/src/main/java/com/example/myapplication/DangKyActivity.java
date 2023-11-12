package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangKyActivity extends AppCompatActivity {
    Button btTroVe,btDangKy;
    EditText edttenDK,edtMatKhau,edtNLMatKhau;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent i=new Intent(getApplicationContext(), Main_Home.class);
//            startActivity(i);
//            finish();
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        btDangKy = findViewById(R.id.btDangKy);
        btTroVe = findViewById(R.id.btTroVe);
        edttenDK=findViewById(R.id.edtTenDK);
        edtMatKhau=findViewById(R.id.edtMatKhau);
        edtNLMatKhau=findViewById(R.id.edtNLMatKhau);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);
        btDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String username,pass, nlpass;
                username=edttenDK.getText().toString();
                pass=edtMatKhau.getText().toString();
                nlpass=edtNLMatKhau.getText().toString();
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(DangKyActivity.this, "Nhap username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(DangKyActivity.this, "Nhap password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nlpass)){
                    Toast.makeText(DangKyActivity.this, "Nhap lai password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(username, pass)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(DangKyActivity.this, "Dang ky thanh cong",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getApplicationContext(), DangNhapActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(DangKyActivity.this, "Dang ky that bai",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        btTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}