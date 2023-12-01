package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.BaiHat;

import java.util.ArrayList;
import java.util.List;

public class BaiHat_Adapter extends RecyclerView.Adapter<BaiHat_Adapter.ViewHolder>{
    private Context c;
    private List<BaiHat> lstBH = new ArrayList<>();

    public BaiHat_Adapter(Context c, List<BaiHat> lstBH) {
        this.c = c;
        this.lstBH = lstBH;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = ((Activity)c).getLayoutInflater().inflate(R.layout.item_nhac,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = lstBH.get(position);

        // Thiết lập số thứ tự cho bài hát
        baiHat.setStt(position + 1);

        holder.tvSTT.setText(String.valueOf(baiHat.getStt()) + ". ");
        holder.tvtenloai.setText(baiHat.getTenBH());
        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if(lstBH != null){
            return lstBH.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSTT,tvtenloai;
        private ImageView imgLove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtenloai = itemView.findViewById(R.id.tvtenloai);
            imgLove = itemView.findViewById(R.id.imgLove);
            tvSTT = itemView.findViewById(R.id.tvSTT);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            itemView.setLayoutParams(layoutParams);
        }
    }
}
