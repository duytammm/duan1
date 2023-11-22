package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class QLUser_Adapter extends RecyclerView.Adapter<QLUser_Adapter.ViewHolder>{
    private Context c;
    private List<User> lst;

    public QLUser_Adapter(Context c, List<User> lst) {
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
        Glide.with(c).load(Uri.parse(user.getAvatar())).error(R.drawable.avatar_null)
                .into(holder.imgAvatar);
        holder.tvMail.setText("Mail: " + user.getEmail());
        holder.tvMK.setText("Mật khẩu: " + user.getMatKhau());
        holder.tvHoTen.setText("Họ tên: " + user.getHotenUser());
        holder.tvSdt.setText("Sdt: " + user.getSdt());

        //Chuyển đổi dữ liệu số sang văn bản
        String mRole="";
        switch(user.getRole()){
            case 1:{
                mRole = "Người dùng";
                holder.tvRole.setText("Role: " + mRole);
                break;
            }
            case 2:{
                mRole = "Nghệ sĩ";
                holder.tvRole.setText("Role: " + mRole);
                break;
            }
            case 3:{
                mRole = "Quản trị viên";
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

        holder.itemUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog(lst.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lst != null) {
            return lst.size();
        }
        return 0;
    }

    private void showUpdateDialog(User user) {
        AlertDialog.Builder bd = new AlertDialog.Builder(c);
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        View v = inf.inflate(R.layout.dialog_update_user,null);
        bd.setView(v);
        AlertDialog alertDialog = bd.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        EditText edtMail = v.findViewById(R.id.edtEmail);
        EditText edtPass = v.findViewById(R.id.edtPass);
        EditText edtFullName = v.findViewById(R.id.edtFullName);
        EditText edtPhone = v.findViewById(R.id.edtPhone);
        EditText edtStatus = v.findViewById(R.id.edtStatus);
        EditText edtRole = v.findViewById(R.id.edtRole);
        Button btUpdate = v.findViewById(R.id.btUpdate);
        Button btHuy = v.findViewById(R.id.btHuy);

        edtMail.setText(user.getEmail());
        edtPass.setText(user.getMatKhau());
        edtFullName.setText(user.getHotenUser());
        edtPhone.setText(user.getSdt());
        edtStatus.setText(String.valueOf(user.getTrangThai()));
        edtRole.setText(String.valueOf(user.getRole()));

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = edtMail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String fullName = edtFullName.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                int status = Integer.parseInt(edtStatus.getText().toString().trim());;
                int role = Integer.parseInt(edtRole.getText().toString().trim());

                if(TextUtils.isEmpty(mail)) {
                    edtMail.setError("Email null");
                    return;
                } else {
                    updateABoolean(user.getIdUser(), mail, pass, fullName, phone, status, role,user.getAvatar());
                    Toast.makeText(c, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }

    private boolean updateABoolean(String id, String mail, String pass, String name, String phone, int status, int role, String avatar) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("USER").child(id);
        User user = new User(id,mail,pass,name,phone,status,role,avatar);

        databaseReference.setValue(user);
        Toast.makeText(c, "Update success", Toast.LENGTH_SHORT).show();
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView  tvMail, tvMK, tvHoTen, tvTrangThai, tvSdt, tvRole;
        private CardView itemUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvMail = itemView.findViewById(R.id.tvMail);
            tvMK = itemView.findViewById(R.id.tvMK);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            tvRole = itemView.findViewById(R.id.tvRole);
            tvSdt = itemView.findViewById(R.id.tvSdt);
            itemUser = itemView.findViewById(R.id.itemUser);
        }
    }
}
