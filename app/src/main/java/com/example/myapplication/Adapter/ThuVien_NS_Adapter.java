package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Lst_NSSongTC_Activity;
import com.example.myapplication.R;
import com.example.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;

public class ThuVien_NS_Adapter extends RecyclerView.Adapter<ThuVien_NS_Adapter.ViewHolder> {
    private Context c;
    private List<User> lstUsers;

    public ThuVien_NS_Adapter(Context c, List<User> lstUsers) {
        this.c = c;
        this.lstUsers = lstUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((Activity)c).getLayoutInflater().inflate(R.layout.item_lst_ns,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User nghesi = lstUsers.get(position);
        holder.tenNS.setText(nghesi.getHotenUser());
        if(nghesi.getAvatar() != null) {
            Glide.with(c).load(Uri.parse(nghesi.getAvatar())).error(R.drawable.avatar_null).into(holder.imgAvatar);
        } else {
            Glide.with(c).load(R.drawable.avatar_null).into(holder.imgAvatar);
        }
        holder.linearNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences NS_tc = c.getSharedPreferences("idNSTC",Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = NS_tc.edit();
                edit.putString("id",nghesi.getIdUser());
                edit.putString("ten",nghesi.getHotenUser());
                edit.putString("anh",nghesi.getAvatar());
                edit.apply();
                c.startActivity(new Intent(c, Lst_NSSongTC_Activity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lstUsers != null) {
            return lstUsers.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tenNS;
        private LinearLayout linearNS;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tenNS = itemView.findViewById(R.id.tenNS);
            linearNS = itemView.findViewById(R.id.linearNS);
        }
    }

    public void updateData(List<User> newData) {
        if (lstUsers == null) {
            lstUsers = new ArrayList<>();
        }

        for (User newUser : newData) {
            boolean checkExists = false;
            for (User existingUser : lstUsers) {
                if (existingUser.getIdUser().equals(newUser.getIdUser())) {
                    checkExists = true;
                    break;
                }
            }

            if (!checkExists) {
                lstUsers.add(newUser);
            }
        }

        notifyDataSetChanged();
    }

}
