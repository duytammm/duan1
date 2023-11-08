package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class TK_NguoiDung_fragment extends Fragment {

    private TextView txtDungMP,txtDungTP;
    private CardView cvLichSuNghe,cvPlaylistYT,cvCaiDat,cvDangXuat;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.taikhoannguoidung_fragment,container,false);
        txtDungMP = v.findViewById(R.id.txtDungMP);
        txtDungTP = v.findViewById(R.id.txtDungTP);
        cvLichSuNghe = v.findViewById(R.id.cvLichSuNghe);
        cvPlaylistYT = v.findViewById(R.id.cvPlaylistYT);
        cvCaiDat = v.findViewById(R.id.cvCaiDat);
        cvDangXuat = v.findViewById(R.id.cvDangXuat);
        return v;
    }
}
