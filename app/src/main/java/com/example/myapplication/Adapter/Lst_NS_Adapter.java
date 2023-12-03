package com.example.myapplication.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.ListNS_Activity;
import com.example.myapplication.R;
import com.example.myapplication.model.BoSuuTap_NS;
import com.example.myapplication.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Lst_NS_Adapter extends RecyclerView.Adapter<Lst_NS_Adapter.ViewHolder> implements Filterable {
    private Context c;
    private List<User> lstNS = new ArrayList<>();
    private List<User> lstSearch = new ArrayList<>();
    private ProgressDialog dialog;
//    int status = 0;

    public Lst_NS_Adapter(Context c, List<User> lstNS) {
        this.c = c;
        this.lstNS = lstNS;
        this.lstSearch = lstNS; // Tạo bản sao của danh sách để làm dữ liệu gốc khi lọc
        dialog = new ProgressDialog(c);
        dialog.setTitle("Thong bao!");
        dialog.setMessage("Loading...");
    }

//    public void setStatus(int status) {
//        this.status = status;
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((Activity)c).getLayoutInflater().inflate(R.layout.item_lst_ns,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User nghesi = lstNS.get(position);
        holder.tenNS.setText(nghesi.getHotenUser());
        if(nghesi.getAvatar() != null) {
            Glide.with(c).load(Uri.parse(nghesi.getAvatar())).error(R.drawable.avatar_null).into(holder.imgAvatar);
        } else {
            Glide.with(c).load(R.drawable.avatar_null).into(holder.imgAvatar);
        }

        // Bắt sự kiện khi người dùng chọn một nghệ sĩ
        holder.linearNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ID nguoidung
                SharedPreferences sharedPreferences1 = c.getSharedPreferences("DataUser",MODE_PRIVATE);
                String idND = sharedPreferences1.getString("id","");

                SharedPreferences nsSharedPreferences = c.getSharedPreferences("idNS",Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = nsSharedPreferences.edit();
                edit.putString("id",nghesi.getIdUser());
//                    edit.putString("name", nghesi.getHotenUser());
//                    edit.putString("avatar", nghesi.getAvatar());
                edit.apply();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BST_NS");
                reference.orderByChild("idND").equalTo(idND).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dialog.show();
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                BoSuuTap_NS boSuuTapNs = dataSnapshot.getValue(BoSuuTap_NS.class);
                                if (nghesi.getIdUser().equals(boSuuTapNs.getIdNS())) {
                                    dialog.dismiss();
                                    Toast.makeText(c, "Ca Sĩ đã được bạn thêm vào trước đó!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        dialog.dismiss();
                        addSingerToBoSuuTapNS(idND, nghesi.getIdUser());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialog.dismiss();
                        Toast.makeText(c, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void addSingerToBoSuuTapNS(String idND, String idNS) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BST_NS");
        String idBst = reference.push().getKey();
        BoSuuTap_NS boSuuTap_ns = new BoSuuTap_NS(idBst, idND, idNS);
        reference.child(idBst).setValue(boSuuTap_ns);
        Toast.makeText(c, "Thêm thành công.", Toast.LENGTH_SHORT).show();
        ((ListNS_Activity) c).finish();
    }

    @Override
    public int getItemCount() {
        if(lstNS != null) {
            return lstNS.size();
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()) {
                    lstNS = lstSearch;
                } else {
                    List<User> lsUsers = new ArrayList<>();
                    for(User user : lstSearch) {
                        if(user.getHotenUser().toLowerCase().contains(strSearch.toLowerCase())) {
                            lsUsers.add(user);
                        }
                    }
                    lstNS = lsUsers;
                }
                FilterResults results = new FilterResults();
                results.values = lstNS;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lstNS = (List<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
