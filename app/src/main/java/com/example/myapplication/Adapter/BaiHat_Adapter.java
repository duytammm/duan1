package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.BaiHat;

import java.util.List;

public class BaiHat_Adapter extends RecyclerView.Adapter<BaiHat_Adapter.ViewHolder>{
    private Context c;
    private List<BaiHat> lstBH;

    public BaiHat_Adapter(Context c, List<BaiHat> lstBH) {
        this.c = c;
        this.lstBH = lstBH;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nghesi,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = lstBH.get(position);
//        holder.imgCaSi
//        Glide.with(c).load(baiHat.getAnhCaSi()).into(holder.imgCaSi);
//        Glide.with(c)
//                .load(baiHat.getAnhCaSi())
//                .into(holder.imgCaSi);
        holder.tvtenCS.setText(baiHat.getTenCaSi());
    }

    @Override
    public int getItemCount() {
        if(lstBH != null) {
            return lstBH.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCaSi;
        private TextView tvtenCS;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCaSi = itemView.findViewById(R.id.imgCaSi);
            tvtenCS = itemView.findViewById(R.id.tvtenCS);
        }
    }

}
