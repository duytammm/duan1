package com.example.myapplication.DAO;

import androidx.annotation.NonNull;

import com.example.myapplication.model.User;
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
    public User_DAO() {
        // Khởi tạo Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference();
        lstUser = new ArrayList<>();
    }

    //LẤY TẤT CẢ THÔNG TIN USER
    public List<User> getUserList() {
//        DatabaseReference userListRef = mDatabase.child("USER");
//        userListRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    String idUser = userSnapshot.getKey();
//                    int trangThai = userSnapshot.child("TrangThai").getValue(Integer.class);
//                    int role = userSnapshot.child("Role").getValue(Integer.class);
//                    String email = userSnapshot.child("Email").getValue(String.class);
//                    String matKhau = userSnapshot.child("MatKhau").getValue(String.class);
//                    String hoTenUser = userSnapshot.child("HotenUser").getValue(String.class);
//                    String sdt = userSnapshot.child("Sdt").getValue(String.class);
//                    String ngaySinh = userSnapshot.child("NgaySinh").getValue(String.class);
//                    String avatar = userSnapshot.child("Avatar").getValue(String.class);
//
//                    User user = new User();
//                    user.setIdUser(idUser);
//                    user.setTrangThai(trangThai);
//                    user.setRole(role);
//                    user.setEmail(email);
//                    user.setMatKhau(matKhau);
//                    user.setHoTenUser(hoTenUser);
//                    user.setSdt(sdt);
//                    user.setNgaySinh(ngaySinh);
//                    user.setAvatar(avatar);
//
//                    lstUser.add(user);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Xảy ra lỗi khi truy vấn database
//            }
//        });
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

    //ĐĂNG KÝ
    public void registerUser(String email, String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("USER");

        String id = reference.push().getKey();
        User user = new User(id,email,password,"","",1,1,"");
//        HashMap hashMap = new HashMap();
//        hashMap.put("email",email);
//        hashMap.put("matkhau",password);
//        hashMap.put("trangthai",1);
//        hashMap.put("role",1);
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
}
