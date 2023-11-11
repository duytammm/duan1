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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangNhapActivity extends AppCompatActivity {
    EditText edtTenDN,edtMatKhau;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i=new Intent(getApplicationContext(), Main_Home.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        Button btDN = findViewById(R.id.btDangNhap);
        Button btDK = findViewById(R.id.btDangKy);
        edtTenDN=findViewById(R.id.edtTenDN);
        edtMatKhau=findViewById(R.id.edtMatKhau);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);

        btDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String username,pass, nlpass;
                username=edtTenDN.getText().toString();
                pass=edtMatKhau.getText().toString();
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(DangNhapActivity.this, "Nhap username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(DangNhapActivity.this, "Nhap password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(username, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(DangNhapActivity.this, "Dang nhap thanh cong",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getApplicationContext(), Main_Home.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(DangNhapActivity.this, "Dang nhap that bai",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        btDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));
            }
        });
    }
}