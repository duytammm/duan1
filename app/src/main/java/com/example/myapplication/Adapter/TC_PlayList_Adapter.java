package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.PlayList_Activity;
import com.example.myapplication.R;
import com.example.myapplication.model.PlayList;

import java.util.ArrayList;

public class TC_PlayList_Adapter extends RecyclerView.Adapter<TC_PlayList_Adapter.ViewHolder>{
    private ArrayList<PlayList> lstPlayList;
    private Context c;

    public TC_PlayList_Adapter(ArrayList<PlayList> lstPlayList, Context c) {
        this.lstPlayList = lstPlayList;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  v = ((Activity)c).getLayoutInflater().inflate(R.layout.item_playlist_ngang,null,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayList playlist = lstPlayList.get(position);
        holder.tvTieuDe.setText(playlist.getTenPlayList());
        if (playlist.getBiaPlayList() != null) {
            Glide.with(c).load(Uri.parse(playlist.getBiaPlayList())).error(R.drawable.avatar_null).into(holder.imgplaylist);
        } else {
            holder.imgplaylist.setImageResource(R.drawable.avatar_null);
        }

//        Glide.with(c).load(Uri.parse(playlist.getBiaPlayList())).error(R.drawable.avatar_null).into(holder.imgPlaylist);
        holder.playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.startActivity(new Intent(c, PlayList_Activity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lstPlayList != null) {
            return lstPlayList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout playlist;
        private ImageView imgplaylist;
        private TextView tvTieuDe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgplaylist = itemView.findViewById(R.id.imgplaylist);
            tvTieuDe = itemView.findViewById(R.id.tvTieuDe);
            playlist = itemView.findViewById(R.id.playlist);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            itemView.setLayoutParams(layoutParams);
        }
    }
}
