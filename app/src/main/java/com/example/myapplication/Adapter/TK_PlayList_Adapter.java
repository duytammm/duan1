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
import com.example.myapplication.model.PlayList;

import java.util.List;

public class TK_PlayList_Adapter extends RecyclerView.Adapter<TK_PlayList_Adapter.ViewHolder>{

    private List<PlayList> lstPL;
    private Context c;

    public TK_PlayList_Adapter(List<PlayList> lstPL, Context c) {
        this.lstPL = lstPL;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayList playList = lstPL.get(position);

        holder.tvTieuDe.setText(playList.getTenPlayList());
    }

    @Override
    public int getItemCount() {
        if(lstPL != null) {
            return lstPL.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgPlaylist;
        private TextView tvTieuDe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlaylist = itemView.findViewById(R.id.imgPlaylist);
            tvTieuDe = itemView.findViewById(R.id.tvTieuDe);
        }
    }
}
