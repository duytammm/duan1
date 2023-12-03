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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TK_QuanLy_fragment extends Fragment {
    final private Update_User updateUser = new Update_User();
    private ImageView imgAvatar;
    private TextView tvTenND, tvEmail;
    private CardView cvQLTaiKhoan, cvTaoTK, cvQLBH, cvQLHD, cvSetting, cvDX;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.taikhoanquanly_fragment,container,false);
        cvQLTaiKhoan = v.findViewById(R.id.cvQLTaiKhoan);
        cvTaoTK = v.findViewById(R.id.cvTaoTK);
        cvQLBH = v.findViewById(R.id.cvQLBH);
        cvQLHD = v.findViewById(R.id.cvQLHD);
        cvSetting = v.findViewById(R.id.cvSetting);
        cvDX = v.findViewById(R.id.cvDX);
        imgAvatar = v.findViewById(R.id.imgAvatar);
        tvTenND = v.findViewById(R.id.tvTenND);
        tvEmail = v.findViewById(R.id.tvEmail);

        showInformation();

        SharedPreferences inforSharedPreferences = getActivity().getSharedPreferences("DataUser",MODE_PRIVATE);
        String mail = inforSharedPreferences.getString("email","");

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Avatar_User").child(mail);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                // Lay link anh
                String imageUrl = downloadUrl.toString();
                Toast.makeText(getActivity(), "Loi: " + imageUrl, Toast.LENGTH_SHORT).show();
                // load len
                Glide.with(getActivity()).load(imageUrl).error(R.drawable.avatar_null).into(imgAvatar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        cvQLTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), QLTK_Activity.class);
                startActivity(i);
            }
        });
        cvDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getContext(), DangNhapActivity.class);
                startActivity(i);
                getActivity().finishAffinity();
            }
        });
        cvTaoTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TaoTaiKhoanActivity.class));
            }
        });
        cvQLBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cvQLHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Update_User.class);
                startActivity(i);
            }
        });

        return v;
    }

    public void showInformation() {
        SharedPreferences inforSharedPreferences = getActivity().getSharedPreferences("DataUser",MODE_PRIVATE);
        String mail = inforSharedPreferences.getString("email","");

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
                            tvEmail.setText(email);
                            tvTenND.setText(hoTen);
                            return;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateUser, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
