package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.HoaDon;
import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TK_NguoiDung_fragment extends Fragment {
    private LinearLayout lnlmusic;
    private ImageView imgAvatar;
    private TextView txtDungMP,txtDungTP, tvTenND, tvMail;
    private CardView cvLichSuNghe,cvPlaylistYT,cvCaiDat,cvDangXuat,idThanhToan;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.taikhoannguoidung_fragment,container,false);
        txtDungMP = v.findViewById(R.id.txtDungMP);
        txtDungTP = v.findViewById(R.id.txtDungTP);
        cvLichSuNghe = v.findViewById(R.id.cvLichSuNghe);
        cvPlaylistYT = v.findViewById(R.id.cvPlaylistYT);
        cvCaiDat = v.findViewById(R.id.cvCaiDat);
        cvDangXuat = v.findViewById(R.id.cvDangXuat);
        tvTenND = v.findViewById(R.id.tvTenND);
        tvMail = v.findViewById(R.id.tvMail);
        imgAvatar = v.findViewById(R.id.imgAvatar);
        idThanhToan = v.findViewById(R.id.idThanhToan);
        lnlmusic = v.findViewById(R.id.lnlmusic);

        showInformation();
        clickListener();
        checkHoaDON();

        return v;
    }

    private void showInformation() {
        SharedPreferences inforSharedPreferences = getActivity().getSharedPreferences("DataUser",MODE_PRIVATE);
        String mail = inforSharedPreferences.getString("email","");

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Avatar_User");
        storageReference.child(mail).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                // Lay link anh
                String imageUrl = downloadUrl.toString();
                Toast.makeText(getActivity(), "Loi: " + imageUrl, Toast.LENGTH_SHORT).show();
                // load len
                Glide.with(TK_NguoiDung_fragment.this).load(imageUrl).error(R.drawable.avatar_null).into(imgAvatar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        DatabaseReference inforReference = FirebaseDatabase.getInstance().getReference("USER");
        inforReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        String email = user.getEmail();
                        String hoTen = user.getHotenUser();
                        if(mail.equals(email)) {
                            tvMail.setText(email);
                            tvTenND.setText(hoTen);
                            return;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickListener() {
        cvDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DangNhapActivity.class);
                startActivity(i);
                getActivity().finishAffinity();
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Update_User.class);
                startActivity(i);
            }
        });

        cvPlaylistYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),PlayList_YeuThich_Activity.class));
            }
        });

        cvLichSuNghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),LichSu_BH_ND_Activity.class));
            }
        });

        cvCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),SettingActivity.class));
            }
        });

        idThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ThanhToan_Activity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        checkHoaDON();
    }

    private void checkHoaDON() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DataUser", MODE_PRIVATE);
        String idUser = sharedPreferences.getString("id", "");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("HOADON");
        reference.orderByChild("idUser").equalTo(idUser)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                HoaDon hoaDon = dataSnapshot.getValue(HoaDon.class);
                                if (hoaDon.getTrangthai() == 1) {
                                    idThanhToan.setVisibility(View.GONE);
                                    lnlmusic.setVisibility(View.GONE);
                                } else {
                                    idThanhToan.setVisibility(View.VISIBLE);
                                    lnlmusic.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Xử lý khi có lỗi
                    }
                });
    }


}
