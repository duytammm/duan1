package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.BaiHat_Adapter;
import com.example.myapplication.model.BaiHat;
import com.example.myapplication.model.See;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class XemBH_Fragment extends Fragment {
    private RecyclerView rcvLstMusic;
    private BaiHat_Adapter baiHatAdapter;
    private List<BaiHat> lstBaiHats = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xembh_fragment,container,false);
        rcvLstMusic = view.findViewById(R.id.rcvLstMusic);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DataID",MODE_PRIVATE);
        String idUserCurrent = sharedPreferences.getString("id","");

        getDataAndDisplay(idUserCurrent);

        return view;
    }

    private void getDataAndDisplay(String idUserCurrent) {
        DatabaseReference seeTableReference = FirebaseDatabase.getInstance().getReference("SEE");

        seeTableReference.orderByChild("idUser").equalTo(idUserCurrent)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@org.checkerframework.checker.nullness.qual.NonNull DataSnapshot snapshot) {
                        for (DataSnapshot seeSnapshot : snapshot.getChildren()) {
                            See seeModel = seeSnapshot.getValue(See.class);
                            if (seeModel != null && seeModel.getIdBH() > 0) {
                                getBaiHatInfo(seeModel.getIdBH());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@org.checkerframework.checker.nullness.qual.NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getBaiHatInfo(int idBH) {
        DatabaseReference baiHatReference = FirebaseDatabase.getInstance().getReference("BAIHAT");

        baiHatReference.child(String.valueOf(idBH))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@org.checkerframework.checker.nullness.qual.NonNull DataSnapshot baiHatSnapshot) {
                        BaiHat baiHatModel = baiHatSnapshot.getValue(BaiHat.class);
                        if (baiHatModel != null) {
                            lstBaiHats.add(baiHatModel);
                            updateRecyclerView();
                        }
                    }

                    @Override
                    public void onCancelled(@org.checkerframework.checker.nullness.qual.NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updateRecyclerView() {
        // Cập nhật RecyclerView
        baiHatAdapter = new BaiHat_Adapter(getActivity(), lstBaiHats);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rcvLstMusic.setLayoutManager(manager);
        rcvLstMusic.setAdapter(baiHatAdapter);
        baiHatAdapter.notifyDataSetChanged();
    }
}
