package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.BaiHat_Adapter;
import com.example.myapplication.model.BaiHat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThuVien_Fragment extends Fragment {

    private ImageView imgNgheGanDay, imgLoc;
    private RecyclerView rcvNgheSi;
    private LinearLayout linearThemNS, linearThemPodcast;
    private List<BaiHat> lstBH;
    private BaiHat_Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.thuvien_fragment,container,false);
        imgNgheGanDay = v.findViewById(R.id.imgNgheGanDay);
        imgLoc = v.findViewById(R.id.imgLoc);
        rcvNgheSi = v.findViewById(R.id.rcvNgheSi);
        linearThemNS = v.findViewById(R.id.linearThemNS);
        linearThemPodcast = v.findViewById(R.id.linearThemPodcast);

        initData();
        getLstNgheSi();

        return v;
    }

    private void initData() {
        lstBH = new ArrayList<>();
        adapter = new BaiHat_Adapter(getContext(),lstBH);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcvNgheSi.setLayoutManager(manager);
        rcvNgheSi.setAdapter(adapter);
    }

    private void getLstNgheSi() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference =  database.getReference("BAIHAT");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BaiHat baihat = dataSnapshot.getValue(BaiHat.class);
                    lstBH.add(baihat);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lấy dữ liệu bị lỗi", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
