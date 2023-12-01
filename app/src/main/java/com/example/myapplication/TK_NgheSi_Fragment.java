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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
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

public class TK_NgheSi_Fragment extends Fragment {
    private ImageView imgAvatar;
    private LinearLayout linearXem, linearThem, linearHistory, linearSua, linearLogout, linearSetting;
    private TextView tvTenND, tvMail;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.taikhoannghesi_fragment,container,false);
        imgAvatar = v.findViewById(R.id.imgAvatar);
        linearXem = v.findViewById(R.id.linearXem);
        linearThem = v.findViewById(R.id.linearThem);
        linearHistory = v.findViewById(R.id.linearHistory);
        linearSua = v.findViewById(R.id.linearSua);
        linearLogout = v.findViewById(R.id.linearLogout);
        linearSetting = v.findViewById(R.id.linearSetting);
        tvTenND = v.findViewById(R.id.tvTenND);
        tvMail = v.findViewById(R.id.tvMail);

        showInformation();
        clickListerner();

        return v;
    }

    public void showInformation() {
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
                Glide.with(getActivity()).load(imageUrl).error(R.drawable.avatar_null).into(imgAvatar);
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

    private void clickListerner() {
        linearThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThemBH_Acitivity.class));
            }
        });
        linearXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), XemBH_Activity.class));
            }
        });

        linearLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DangNhapActivity.class);
                startActivity(i);
                getActivity().finishAffinity();
            }
        });
    }

}
