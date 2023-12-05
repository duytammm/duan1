package com.example.myapplication.DAO;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.myapplication.model.HoaDon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HoaDon_DAO {
    private FirebaseDatabase databaseReference;
    private Context c;
    public HoaDon_DAO(Context c) {
        // Khởi tạo Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance();
        this.c = c;
    }

    public interface getAllHD {
        void ongetSuccess(ArrayList<HoaDon> getHD);
    }

    public void getHD(getAllHD lstHD) {
        DatabaseReference hdReference = databaseReference.getReference("HOADON");
        hdReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<HoaDon> lsthd = new ArrayList<>();
                for(DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    HoaDon hoaDon = dataSnapshot.getValue(HoaDon.class);
                    lsthd.add(hoaDon);
                }
                lstHD.ongetSuccess(lsthd);
            }
        });
    }
}
