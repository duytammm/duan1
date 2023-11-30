package com.example.myapplication.DAO;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.myapplication.model.PlayList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PlayList_DAO {
    private FirebaseDatabase databaseReference;
    private Context c;
    public PlayList_DAO(Context c) {
        // Khởi tạo Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance();
        this.c = c;
    }

    public interface getListPlayList {
        void ongetSuccess(ArrayList<PlayList> ListPlayList);
    }

    public void getPlayList(getListPlayList lstPlayList) {
        DatabaseReference reference = databaseReference.getReference("PLAYLIST");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<PlayList> lstPL = new ArrayList<>();
                for(DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    PlayList playList = dataSnapshot.getValue(PlayList.class);
                    lstPL.add(playList);
                }
                lstPlayList.ongetSuccess(lstPL);
            }
        });
    }

}
