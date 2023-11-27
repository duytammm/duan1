package com.example.myapplication;

import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.DAO.User_DAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaoTaiKhoanActivity extends AppCompatActivity {
    private Toolbar tbTTK;
    private EditText edtEmail, edtPass, edtFullName;
    private Spinner spRole;
    private Button btAdd;
    private User_DAO dao;
    private void initView() {
        tbTTK = findViewById(R.id.tbTTK);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtFullName = findViewById(R.id.edtFullName);
        spRole = findViewById(R.id.spRole);
        btAdd = findViewById(R.id.btAdd);
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
        setContentView(R.layout.activity_tao_tai_khoan);
        initView();

        setSupportActionBar(tbTTK);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle("Tạo tài khoản");

        dao = new User_DAO();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser() {
        String mail = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String fullName = edtFullName.getText().toString().trim();;
        String role = spRole.getSelectedItem().toString();
        int rules = 0 ;
        if(role.equals("Người dùng")) {
            rules = 1;
        } else if(role.equals("Nghệ sĩ")) {
            rules = 2;
        } else if(role.equals("Quản trị viên")) {
            rules = 3;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(this, "Vui lòng nhập mail theo định dạng: abc@....", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userListRef = FirebaseDatabase.getInstance().getReference("USER");
        int finalRules = rules;
        userListRef.orderByChild("email").equalTo(mail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Email is already registered
                    Toast.makeText(TaoTaiKhoanActivity.this, "Email đã tồn tại. Vui lòng chọn email khác!", Toast.LENGTH_SHORT).show();
                } else {
                    // Email is not registered, proceed with the sign-up
                    dao.addUser(mail, pass,fullName,1, finalRules);
                    clearForm();
                    Toast.makeText(TaoTaiKhoanActivity.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TaoTaiKhoanActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm() {
        edtEmail.setText("");
        edtPass.setText("");
        edtFullName.setText("");
    }
}