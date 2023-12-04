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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.BaiHat;
import com.example.myapplication.model.See;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Lst_Song_Adapter extends RecyclerView.Adapter<Lst_Song_Adapter.ViewHolder> implements Filterable {
    private Context c;
    private List<BaiHat> lstBaiHats = new ArrayList<>();
    private List<BaiHat> lstSearch = new ArrayList<>();
    private ProgressDialog dialog;
    public Lst_Song_Adapter(Context c, List<BaiHat> lstBaiHats) {
        this.c = c;
        this.lstBaiHats = lstBaiHats;
        this.lstSearch = lstBaiHats;
        dialog = new ProgressDialog(c);
        dialog.setTitle("Thong bao!");
        dialog.setMessage("Loading...");
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

        SharedPreferences sharedPreferences = c.getSharedPreferences("DataUser",MODE_PRIVATE);
        String idUser = sharedPreferences.getString("id","");
        int idBH = baiHat.getIdBaiHat();

        DatabaseReference PLYeuthich = FirebaseDatabase.getInstance().getReference("SEE");
        PLYeuthich.orderByChild("idUser").equalTo(idUser)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dialog.show();
                        if (snapshot.exists()) {
                            boolean songAdded = false;
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                See see = dataSnapshot.getValue(See.class);
                                if (idBH == see.getIdBH()) {
                                    songAdded = true;
                                    break;
                                }
                            }
                            if (songAdded) {
                                holder.imgLove.setImageResource(R.drawable.red_love);
                            } else {
                                holder.imgLove.setImageResource(R.drawable.heart);
                            }
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = c.getSharedPreferences("DataUser",MODE_PRIVATE);
                String idUser = sharedPreferences.getString("id","");
                int idBH = baiHat.getIdBaiHat();

                DatabaseReference PLYeuthich = FirebaseDatabase.getInstance().getReference("SEE");
                String idSee = PLYeuthich.push().getKey();
                PLYeuthich.orderByChild("idUser").equalTo(idUser)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                dialog.show();
                                if(snapshot.exists()) {
                                    boolean songAdded = false;
                                    String seeIdToRemove = null;

                                    if (snapshot.exists()) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            See see = dataSnapshot.getValue(See.class);
                                            if (idBH == see.getIdBH()) {
                                                songAdded = true;
                                                seeIdToRemove = dataSnapshot.getKey();
                                                break;
                                            }
                                        }

                                        if (songAdded) {
                                            // BH trong PLYT, remove nó khỏi PL
                                            PLYeuthich.child(seeIdToRemove).removeValue();
                                            dialog.dismiss();
                                            holder.imgLove.setImageResource(R.drawable.heart);
                                            Toast.makeText(c, "Đã xóa khỏi danh sách yêu thích.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Bài ahst không trong PLYT thì thêm vào
                                            dialog.dismiss();
                                            holder.imgLove.setImageResource(R.drawable.red_love);
                                            See see = new See(idUser, idSee, idBH);
                                            PLYeuthich.child(idSee).setValue(see);
                                            Toast.makeText(c, "Thêm thành công.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Người dùng không cóa bài hát yt nào thì add mới
                                        dialog.dismiss();
                                        holder.imgLove.setImageResource(R.drawable.red_love);
                                        See see = new See(idUser, idSee, idBH);
                                        PLYeuthich.child(idSee).setValue(see);
                                        Toast.makeText(c, "Thêm thành công.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(c, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                            }
                        });
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
