package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.User_Adapter;
import com.example.myapplication.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QLTK_Activity extends AppCompatActivity {
    private Toolbar tbQLTK;
    private RecyclerView rcvQLTK;
    private User_Adapter adapter;
    private List<User> lstUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltk);
        tbQLTK = findViewById(R.id.tbQLTK);
        rcvQLTK = findViewById(R.id.rcvQLTK);

        setSupportActionBar(tbQLTK);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_music);
        getSupportActionBar().setTitle("Quản lý tài khoản");

        lstUser = new ArrayList<>();
        adapter = new User_Adapter(this,lstUser);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvQLTK.setLayoutManager(manager);
        rcvQLTK.setAdapter(adapter);
        getListUser();

    }

    private void getListUser(){
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

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QLTK_Activity.this, "Lấy danh sách thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

}