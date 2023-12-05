package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CT_Play_nhac;
import com.example.myapplication.R;
import com.example.myapplication.model.BaiHat;

import java.util.List;

public class PLYT_Adapter extends RecyclerView.Adapter<PLYT_Adapter.ViewHolder> {
    private Context c;
    private List<BaiHat> lstBaiHats;

    public PLYT_Adapter(Context c, List<BaiHat> lstBaiHats) {
        this.c = c;
        this.lstBaiHats = lstBaiHats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((Activity)c).getLayoutInflater().inflate(R.layout.item_nhacyt,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = lstBaiHats.get(position);
        baiHat.setStt(position + 1);
        holder.tvSTT.setText(baiHat.getStt() + ". ");
        holder.tvtenloai.setText(baiHat.getTenBH());
        holder.imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chuyển hướng đến CT_Play_nhac
                Intent intent = new Intent(c, CT_Play_nhac.class);
                // Truyền ID BaiHat, sang hoạt động tiếp theo
                intent.putExtra("baihat_id", baiHat.getIdBaiHat());
                c.startActivity(intent);
            }
        });
        holder.PlaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chuyển hướng đến CT_Play_nhac
                Intent intent = new Intent(c, CT_Play_nhac.class);
                // Truyền ID BaiHat, sang hoạt động tiếp theo
                intent.putExtra("baihat_id", baiHat.getIdBaiHat());
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lstBaiHats != null) {
            return lstBaiHats.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSTT,tvtenloai;
        private ImageView imgPlay;
        private CardView PlaySong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtenloai = itemView.findViewById(R.id.tvtenloai);
            imgPlay = itemView.findViewById(R.id.imgPlay);
            tvSTT = itemView.findViewById(R.id.tvSTT);
            PlaySong = itemView.findViewById(R.id.PlaySong);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            itemView.setLayoutParams(layoutParams);
        }
    }
}
