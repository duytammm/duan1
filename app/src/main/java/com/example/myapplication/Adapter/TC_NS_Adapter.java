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
import com.example.myapplication.model.User;

import java.util.List;

public class TC_NS_Adapter extends RecyclerView.Adapter<TC_NS_Adapter.ViewHolder>{
    private List<User> lstNS;
    private Context c;

    public TC_NS_Adapter(List<User> lstNS, Context c) {
        this.lstNS = lstNS;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = ((Activity)c).getLayoutInflater().inflate(R.layout.item_trangchu_ns,null,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User ns = lstNS.get(position);
        if(ns.getHotenUser().equals("")) {
            holder.tvTenNS.setText(ns.getHotenUser() + "Hãy đoán xem tôi là ai!");
        } else {
            holder.tvTenNS.setText(ns.getHotenUser());
        }

        Glide.with(c).load(Uri.parse(ns.getAvatar())).error(R.drawable.avatar_null).into(holder.imgNS);
        holder.linearNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.startActivity(new Intent(c, PlayList_Activity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lstNS != null) {
            return lstNS.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearNS;
        private ImageView imgNS;
        private TextView tvTenNS;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNS = itemView.findViewById(R.id.imgNS);
            tvTenNS = itemView.findViewById(R.id.tvTenNS);
            linearNS = itemView.findViewById(R.id.linearNS);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            itemView.setLayoutParams(layoutParams);
        }
    }
}
