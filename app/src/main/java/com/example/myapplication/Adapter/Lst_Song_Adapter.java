package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.BaiHat;

import java.util.ArrayList;
import java.util.List;

public class Lst_Song_Adapter extends RecyclerView.Adapter<Lst_Song_Adapter.ViewHolder> implements Filterable {
    private Context c;
    private List<BaiHat> lstBaiHats = new ArrayList<>();
    private List<BaiHat> lstSearch = new ArrayList<>();
    public Lst_Song_Adapter(Context c, List<BaiHat> lstBaiHats) {
        this.c = c;
        this.lstBaiHats = lstBaiHats;
        this.lstSearch = lstBaiHats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((Activity)c).getLayoutInflater().inflate(R.layout.item_song_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = lstBaiHats.get(position);
        holder.tvNameSong.setText(baiHat.getTenBH());
        if(baiHat.getBiaBH() != null) {
            Glide.with(c).load(Uri.parse(baiHat.getBiaBH())).error(R.drawable.avatar_null).into(holder.imgSong);
        } else {
            Glide.with(c).load(R.drawable.avatar_null).into(holder.imgSong);
        }
        holder.tvTenCS.setText(baiHat.getTenCaSi());
        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        private ImageView imgSong, imgLove;
        private TextView tvNameSong,tvTenCS;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.imgSong);
            tvNameSong = itemView.findViewById(R.id.tvNameSong);
            tvTenCS = itemView.findViewById(R.id.tvTenCS);
            imgLove = itemView.findViewById(R.id.imgLove);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    lstBaiHats = lstSearch;
                } else {
                    List<BaiHat> lst = new ArrayList<>();
                    for (BaiHat baiHat : lstSearch) {
                        if (baiHat.getTenBH().toLowerCase().contains(strSearch.toLowerCase())) {
                            lst.add(baiHat);
                        }
                    }
                    lstBaiHats = lst;
                }
                FilterResults results = new FilterResults();
                results.values = lstBaiHats;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lstBaiHats = (List<BaiHat>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
