package com.example.myapplication.DAO;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class User_DAO {
    private DatabaseReference databaseReference;
    private List<User> lstUser;
    private Context c;
    public User_DAO(Context c) {
        // Khởi tạo Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference();
        this.c = c;
        lstUser = new ArrayList<>();
    }

    public interface ongetListCS {
        void ongetSuccess(List<User> lstCS);
    }

    //LẤY TẤT CẢ THÔNG TIN USER
    public List<User> getUserList() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("USER");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            // trả về tổng bên ngoài DataSnapshot snapshot
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    lstUser.add(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return lstUser;
    }

    //Get list user co role=2
    public void getLstCS(ongetListCS lst) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("USER");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                List<User> lstCS = new ArrayList<>();
                for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if(user.getRole() == 2) {
                        lstCS.add(user);
                    }
                }
                lst.ongetSuccess(lstCS);
            }
        });
    }


    //ĐĂNG KÝ
    public void registerUser(String email, String password,String hoten) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("USER");

        String id = reference.push().getKey();
        User user = new User(id,email,password,hoten,"",1,1,"");
        reference.child(id).setValue(user);
    }

    //TẠO TÀI KHOẢN
    public void addUser(String mail, String pass, String fullName, int status, int role){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("USER");
        String id = reference.push().getKey();

        User newUser = new User(id,mail,pass,fullName,"",status,role,"");
        reference.child(id).setValue(newUser);
    }

    //THAY THẾ KÍ TỰ TRONG MAIL
    public String replace(String email) {
        // Thay thế các ký tự không hợp lệ trong email
        return email.replace('.', '_')
                .replace('#', '_')
                .replace('$', '_')
                .replace('[', '_')
                .replace(']', '_');
    }

    public interface OnRoleReceivedListener {
        void onRoleReceived(int role);
    }

    public void getCurrentUserRole(OnRoleReceivedListener listener) {
        SharedPreferences inforSharedPreferences = c.getSharedPreferences("DataUser", MODE_PRIVATE);
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
                Toast.makeText(c, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
