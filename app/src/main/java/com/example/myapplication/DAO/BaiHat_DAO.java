package com.example.myapplication.DAO;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.myapplication.model.BaiHat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BaiHat_DAO {
    private FirebaseDatabase database;
    private Context c;
    private SharedPreferences sharedPreferences;
    public BaiHat_DAO(Context c) {
        database = FirebaseDatabase.getInstance();
        this.c = c;
        sharedPreferences = c.getSharedPreferences("DataID",MODE_PRIVATE);
    }

    public interface getLstBH {
        void onSuccess(List<BaiHat> LstbaiHats);
    }

    public void getAllSong(getLstBH lstBH ) {
        DatabaseReference baihatReference = database.getReference("BAIHAT");
        baihatReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                List<BaiHat> lstSong = new ArrayList<>();
                for(DataSnapshot bhSnapshot : task.getResult().getChildren()) {
                    if(bhSnapshot.exists()) {
                        BaiHat bh = bhSnapshot.getValue(BaiHat.class);
                        lstSong.add(bh);
                    }
                }
                lstBH.onSuccess(lstSong);
            }
        });
    }


}
