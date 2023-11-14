package com.example.myapplication.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.User;

import java.util.List;

public class User_Adapter extends RecyclerView.Adapter<User_Adapter.ViewHolder>{
    Context c;
    List<User> lst;

    public User_Adapter(Context c, List<User> lst) {
        this.c = c;
        this.lst = lst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qltk,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = lst.get(position);
        if(user == null) {
            return;
        }
        Glide.with(c).load(Uri.parse(user.getAvatar()))
                        .into(holder.imgAvatar);
        holder.tvID.setText("ID: " + user.getIdUser());
        holder.tvMail.setText("Mail: " + user.getEmail());
        holder.tvMK.setText("Mật khẩu: " + user.getMatKhau());
        holder.tvHoTen.setText("Họ tên: " + user.getHoTenUser());
        holder.tvNgaySinh.setText("Ngày sinh: " + user.getNgaySinh());
        holder.tvSdt.setText("Sdt: " + user.getSdt());

        //Chuyển đổi dữ liệu số sang văn bản
        String mRole="";
        switch(user.getRole()){
            case 1:{
                mRole = "Quản trị viên";
                holder.tvRole.setText("Role: " + mRole);
                break;
            }
            case 2:{
                mRole = "Nghệ sĩ";
                holder.tvRole.setText("Role: " + mRole);
                break;
            }
            case 3:{
                mRole = "Người dùng";
                holder.tvRole.setText("Role: " + mRole);
                break;
            }
        }

        String mTrangThai = "";
        switch(user.getTrangThai()){
            case -1: {
                mTrangThai = "Bị khóa";
                holder.tvTrangThai.setText("Trạng thái: " + mTrangThai);
                break;
            }
            case 0: {
                mTrangThai = "Khóa tạm thời";
                holder.tvTrangThai.setText("Trạng thái: " + mTrangThai);
                break;
            }
            case 1: {
                mTrangThai = "Đang hoạt động";
                holder.tvTrangThai.setText("Trạng thái: " + mTrangThai);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(lst != null) {
            return lst.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvID, tvMail, tvMK, tvHoTen, tvTrangThai, tvSdt, tvNgaySinh, tvRole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvID = itemView.findViewById(R.id.tvID);
            tvMail = itemView.findViewById(R.id.tvMail);
            tvMK = itemView.findViewById(R.id.tvMK);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            tvRole = itemView.findViewById(R.id.tvRole);
            tvNgaySinh = itemView.findViewById(R.id.tvNgaySinh);
            tvSdt = itemView.findViewById(R.id.tvSdt);
        }
    }
}
