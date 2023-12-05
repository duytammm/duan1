package com.example.myapplication.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.CT_Playlist_AddSong;
import com.example.myapplication.R;
import com.example.myapplication.model.PlayList;

import java.util.List;

public class QLPLBH_Adapter extends RecyclerView.Adapter<QLPLBH_Adapter.ViewHoder> {
    private Context c;
    private List<PlayList> lstPlayLists;

    public QLPLBH_Adapter(Context c, List<PlayList> lstPlayLists) {
        this.c = c;
        this.lstPlayLists = lstPlayLists;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = ((Activity)c).getLayoutInflater().inflate(R.layout.item_qlplbh,parent,false);
        return new ViewHoder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        PlayList playList = lstPlayLists.get(position);
        holder.tvtenloai.setText(playList.getTenPlayList());
        if(playList.getBiaPlayList() != null) {
            Glide.with(c).load(Uri.parse(playList.getBiaPlayList())).error(R.drawable.avatar).into(holder.imgBiaPL);
        } else {
            Glide.with(c).load(R.drawable.avatar).into(holder.imgBiaPL);
        }

        holder.PlaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = c.getSharedPreferences("idPL",Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("id",playList.getIdPlayList());
                edit.putString("ten", playList.getTenPlayList());
                edit.apply();
                c.startActivity(new Intent(c, CT_Playlist_AddSong.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lstPlayLists != null) {
            return lstPlayLists.size();
        }
        return 0;
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        private TextView tvtenloai;
        private ImageView imgBiaPL;
        private CardView PlaySong;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvtenloai = itemView.findViewById(R.id.tvtenloai);
            imgBiaPL = itemView.findViewById(R.id.imgBiaPL);
            PlaySong = itemView.findViewById(R.id.PlaySong);
        }
    }
}
