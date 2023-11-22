package com.example.myapplication;

import android.content.Intent;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TK_QuanLy_fragment extends Fragment {
    final private Update_User updateUser = new Update_User();
    public static final int MY_REQUEST_CODE = 10;
    private ImageView imgAvatar;
    private TextView tvTenND, tvEmail;
    private CardView cvQLTaiKhoan, cvTaoTK, cvQLBH, cvQLHD, cvSetting, cvDX;
//    final private ActivityResultLauncher<Intent> mactivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if(result.getResultCode() == RESULT_OK) {
//                        Intent intent = result.getData();
//                        if(intent == null) {
//                            return;
//                        } else {
//                            Uri uri = intent.getData();
//                            updateUser.setUri(uri);
//                            try {
//                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
//                                updateUser.setBitmap(bitmap);
//                            } catch (IOException e) {
//                                Toast.makeText(getContext(), "Lỗi: " + e, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                }
//            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        showInfo();

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

    public void showInfo() {

//        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }

        if (user != null) {
            // Name, email address, and profile photo Url
            String userID = user.getUid();
            String name = user.getDisplayName();
            String email = user.getEmail();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(userID);

            if(name == null) {
                tvTenND.setVisibility(View.GONE);
            } else {
                tvTenND.setVisibility(View.VISIBLE);
                tvTenND.setText(name);
            }
            tvEmail.setText(email);
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri downloadUrl) {
                    // Lay link anh
                    String imageUrl = downloadUrl.toString();
                    Toast.makeText(updateUser, "Loi: " + imageUrl, Toast.LENGTH_SHORT).show();
                    // load len
                    Glide.with(getActivity()).load(imageUrl).error(R.drawable.avatar_null).into(imgAvatar);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        }
    }

}
