package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DAO.User_DAO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        dao = new User_DAO();

    }

    private void signUp() {
        dialog.show();
        String mail = edtMail.getText().toString();
        String pass = edtMatKhau.getText().toString();
        String rePass = edNLMatKhau.getText().toString();

        if (mail.equals("") || pass.equals("") || rePass.equals("")) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
        if (!pass.equals(rePass)) {
            Toast.makeText(this, "Vui lòng nhập 2 pass trùng nhau!", Toast.LENGTH_SHORT).show();
        }
//        if(pass.length() < 6 && rePass.length() < 6) {
//            Toast.makeText(this, "Mật khẩu ít nhất 6 kí tự!!!", Toast.LENGTH_SHORT).show();
//        }

//        FirebaseAuth mAuth;
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("USER");
        if (Patterns.EMAIL_ADDRESS.matcher(mail).matches() && !pass.equals("") && !rePass.equals("") && pass.equals(rePass)) {
            dao.registerUser(mail, pass);
            startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
            finishAffinity();
            Toast.makeText(DangKyActivity.this, "Register success", Toast.LENGTH_SHORT).show();
//        mAuth.createUserWithEmailAndPassword(mail, pass)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        dialog.dismiss();
//                        if (task.isSuccessful()) {
//                            dialog.dismiss();
//                            dao.registerUser(mail, pass);
//                            startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
//                            finishAffinity();
//                            Toast.makeText(DangKyActivity.this, "Register success", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(DangKyActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
        } else {
            Toast.makeText(this, "Vui lòng nhập mail theo định dạng: abc@....", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}

//    private void signUp() {
//        dialog.show();
//        mAuth=FirebaseAuth.getInstance();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("USER");
//
//        String email = edtTenDK.getText().toString().trim();
//        String password = edtMatKhau.getText().toString().trim();
//        String repassword = edNLMatKhau.getText().toString().trim();
//        //Tao userID cho moi nguoi dung duoc them vao
//        String userID = reference.push().getKey();
//
//        if (TextUtils.isEmpty(email)){
//            Toast.makeText(DangKyActivity.this, "Nhap username", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(password)){
//            Toast.makeText(DangKyActivity.this, "Nhap password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(repassword)){
//            Toast.makeText(DangKyActivity.this, "Nhap lai password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(!password.equals(repassword)) {
//            Toast.makeText(this, "Pass khong trung nhau, vui long nhap lai", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(password.length() < 6 || repassword.length() < 6) {
//            Toast.makeText(this, "Vui long nhap mat khau it nhat 6 kitu bao gom chu va so", Toast.LENGTH_SHORT).show();
//        }
//
//        if(!email.equals("") && !password.equals("") &&  !repassword.equals("") && password.equals(repassword)) {
//            mAuth.createUserWithEmailAndPassword(email,password)
//                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            dialog.dismiss();
////                            User user = new User(email,password,3,1);
////                            reference.child(userID).setValue(user);
//                            if (task.isSuccessful()) {
//                                Toast.makeText(DangKyActivity.this, "Dang ky thanh cong",
//                                        Toast.LENGTH_SHORT).show();
//                                Intent i=new Intent(DangKyActivity.this, DangNhapActivity.class);
//                                startActivity(i);
//                                finish();
//                            } else {
//                                Toast.makeText(DangKyActivity.this, "Dang ky that bai", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        } else {
//            Toast.makeText(this, "Dang ky that bai", Toast.LENGTH_SHORT).show();
//        }
//    }
