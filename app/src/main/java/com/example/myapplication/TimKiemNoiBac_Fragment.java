package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.AllSong_Adapter;
import com.example.myapplication.DAO.BaiHat_DAO;
import com.example.myapplication.model.BaiHat;

import java.util.List;

public class TimKiemNoiBac_Fragment extends Fragment {
    private RecyclerView rcvNoiBac;
    private BaiHat_DAO baiHatDao;
    private AllSong_Adapter allSongAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.timkiem_noibac_fragment,container,false);
        rcvNoiBac = v.findViewById(R.id.rcvNoiBac);

        baiHatDao = new BaiHat_DAO(getActivity());
        baiHatDao.getAllSong(new BaiHat_DAO.getLstBH() {
            @Override
            public void onSuccess(List<BaiHat> LstbaiHats) {
                allSongAdapter = new AllSong_Adapter(getActivity(),LstbaiHats);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                rcvNoiBac.setLayoutManager(manager);
                rcvNoiBac.setAdapter(allSongAdapter);
                allSongAdapter.notifyDataSetChanged();
            }
        });
        return v;
    }
}
