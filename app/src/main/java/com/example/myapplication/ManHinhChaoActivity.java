package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DAO.User_DAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManHinhChaoActivity extends AppCompatActivity {
    private User_DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        dao = new User_DAO(this);

        CountDownTimer timer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                nextActivity();
            }
        };
        timer.start();
    }

    private void nextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            //Chua Login
            Intent i = new Intent(this, DangNhapActivity.class);
            startActivity(i);
        } else {
            //Da Login
            dao.getCurrentUserRole(new User_DAO.OnRoleReceivedListener() {
                @Override
                public void onRoleReceived(int role) {
                    switch (role) {
                        case 1: {
                            Intent i = new Intent(ManHinhChaoActivity.this,MainActivity_NguoiDung.class);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case 2: {
                            Intent i = new Intent(ManHinhChaoActivity.this,MainActivity_NgheSi.class);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case 3: {
                            Intent i = new Intent(ManHinhChaoActivity.this,MainActivity_QuanLy.class);
                            startActivity(i);
                            finish();
                            break;
                        }
                    }
                }
            });

        }

    }
}