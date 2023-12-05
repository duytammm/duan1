package com.example.myapplication.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CT_Play_nhac;
import com.example.myapplication.CT_Playlist_AddSong;
import com.example.myapplication.R;
import com.example.myapplication.model.BaiHat;
import com.example.myapplication.model.PlayList_BaiHat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class AllSong_Adapter extends RecyclerView.Adapter<AllSong_Adapter.Viewholder> {
    private Context c;
    private List<BaiHat> lstBaiHats;
    private ProgressDialog dialog;
    private SharedPreferences sharedPreferences;

    public AllSong_Adapter(Context c, List<BaiHat> lstBaiHats) {
        this.c = c;
        this.lstBaiHats = lstBaiHats;
        dialog = new ProgressDialog(c);
        dialog.setTitle("Thong bao!");
        dialog.setMessage("Dang them....");
        sharedPreferences = c.getSharedPreferences("idPL",Context.MODE_PRIVATE);;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = ((Activity)c).getLayoutInflater().inflate(R.layout.item_nhacpl,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        BaiHat baiHat = lstBaiHats.get(position);
        holder.tvtenloai.setText(baiHat.getTenBH());
        holder.imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chuyển hướng đến CT_Play_nhac
                Intent intent = new Intent(c, CT_Play_nhac.class);
                // Truyền ID BaiHat, sang hoạt động tiếp theo
                intent.putExtra("baihat_id", baiHat.getIdBaiHat());
                c.startActivity(intent);
            }
        });
        holder.PlaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String idPL = sharedPreferences.getString("id","");
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PLAYLIST_BAIHAT");
                String idplbh = reference.push().getKey();

                reference.orderByChild("idpl").equalTo(idPL).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean songExists = false;
                        if(snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                PlayList_BaiHat playlistBaiHat = dataSnapshot.getValue(PlayList_BaiHat.class);
                                if (playlistBaiHat != null && playlistBaiHat.getIdbh() == baiHat.getIdBaiHat()) {
                                    // Bài hát đã tồn tại trong danh sách pl
                                    songExists = true;
                                    break;
                                }
                            }

                            if (!songExists) {
                                // Thêm bài hát vào danh sách pl
                                PlayList_BaiHat playListBaiHat = new PlayList_BaiHat(idplbh, idPL, baiHat.getIdBaiHat());
                                reference.child(idplbh).setValue(playListBaiHat);
                                dialog.dismiss();
                                Toast.makeText(c, "Thêm thành công.", Toast.LENGTH_SHORT).show();
                                c.startActivity(new Intent(c, CT_Playlist_AddSong.class));
                            } else {
                                dialog.dismiss();
                                Toast.makeText(c, "Bài hát đã tồn tại trong playlist.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            PlayList_BaiHat playListBaiHat = new PlayList_BaiHat(idplbh, idPL, baiHat.getIdBaiHat());
                            reference.child(idplbh).setValue(playListBaiHat);
                            dialog.dismiss();
                            Toast.makeText(c, "Thêm thành công.", Toast.LENGTH_SHORT).show();
                            c.startActivity(new Intent(c, CT_Playlist_AddSong.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
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

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView tvtenloai;
        private CardView PlaySong;
        private ImageView imgPlay;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            PlaySong = itemView.findViewById(R.id.PlaySong);
            tvtenloai = itemView.findViewById(R.id.tvtenloai);
            imgPlay = itemView.findViewById(R.id.imgPlay);
        }
    }
}
