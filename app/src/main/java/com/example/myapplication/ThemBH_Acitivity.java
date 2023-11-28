package com.example.myapplication;


import static com.example.myapplication.Update_User.MY_REQUEST_CODE;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.DAO.User_DAO;
import com.example.myapplication.model.BaiHat;
import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThemBH_Acitivity extends AppCompatActivity {
    public User_DAO userDao;
    private static final int PICK_AUDIO_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST = 71;
    private ImageView imgBiaBH;
    private EditText edtTenBH,edtLinkBH;
    private Spinner spTenCS;
    private Button btThem;
    private Toolbar tbThemBH;
    private Uri audioUri, imgUri;
    private void initView() {
        imgBiaBH = findViewById(R.id.imgBiaBH);
        edtTenBH = findViewById(R.id.edtTenBH);
        edtLinkBH = findViewById(R.id.edtLinkBH);
        spTenCS = findViewById(R.id.spTenCS);
        btThem = findViewById(R.id.btThem);
        tbThemBH = findViewById(R.id.tbThemBH);
    }
    private void setSpinner() {
        final SimpleAdapter[] simpleAdapter = new SimpleAdapter[1];
        userDao.getLstCS(new User_DAO.ongetListCS() {
            @Override
            public void ongetSuccess(List<User> lstCS) {
                List<HashMap<String,Object>> lstHm = new ArrayList<>();

                for(User user : lstCS) {
                    HashMap<String,Object> hm = new HashMap<>();
                    hm.put("id",user.getIdUser());
                    hm.put("mail",user.getEmail());
                    hm.put("mk",user.getMatKhau());
                    hm.put("hoten",user.getHotenUser());
                    hm.put("trangthai",user.getTrangThai());
                    hm.put("role",user.getRole());
                    hm.put("avatar",user.getAvatar());
                    lstHm.add(hm);
                }
                simpleAdapter[0] = new SimpleAdapter(
                        ThemBH_Acitivity.this,
                        lstHm,
                        R.layout.sp_tencasi,
                        new String[]{"hoten"},
                        new int[]{R.id.tvTenCS}
                );
                spTenCS.setAdapter(simpleAdapter[0]);

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Thực hiện hành động quay về trang trước đó
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_bh_acitivity);
        initView();
        userDao = new User_DAO();
        setSpinner();

        setSupportActionBar(tbThemBH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle("Thêm bài hát");

        imgBiaBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        edtLinkBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAudio();
            }
        });
        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBaiHat();
            }
        });
    }

    //Chọn nhạc
    private void chooseAudio() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn bài hát"), PICK_AUDIO_REQUEST);
    }

    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if(intent == null) {
                            return;
                        } else {
                            Uri uri = intent.getData();
                            try {
                                imgUri = uri;
                                Glide.with(ThemBH_Acitivity.this).load(imgUri).into(imgBiaBH);
                            } catch (Exception e) {
                                Toast.makeText(ThemBH_Acitivity.this, "Lỗi: " + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });

    //Chọn ảnh bìa
    private void checkPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Vui lòng mở quyền!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_AUDIO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            audioUri = data.getData();
            String fileName = audioUri.toString();
            edtLinkBH.setText(fileName);
            // Upload bài hát lên Firebase Storage
//            uploadAudioToFirebaseStorage();
        }

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            Uri selecteduri = data.getData();
            imgUri = selecteduri;
            Glide.with(this).load(selecteduri).into(imgBiaBH);
        }
    }

    private void uploadBaiHat() {
        final String[] mUri = {""};
        BaiHat newBaiHat = new BaiHat();
        if (audioUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Đang thêm...");
            progressDialog.show();

            //Lấy dữ liệu
            String tenBH = edtTenBH.getText().toString();
            String tenCS = spTenCS.getSelectedItem().toString();
            newBaiHat.setTenCaSi(tenCS);
            newBaiHat.setTenBH(tenBH);

            //Lấy id
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference baihatReference = database.getReference("BAIHAT");
            baihatReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();
                    List<BaiHat> baihatList = new ArrayList<>();
                    // Duyệt qua tất cả các nút con trong nút "baihat"
                    for (DataSnapshot baihatSnapshot : dataSnapshot.getChildren()) {
                        // Lấy giá trị của nút con hiện tại và chuyển đổi thành đối tượng BaiHat
                        BaiHat baihat = baihatSnapshot.getValue(BaiHat.class);
                        baihatList.add(baihat);
                    }
                    // Tính toán giá trị id cho BaiHat tiếp theo
                    int nextIdBaiHat = 0;
                    for (BaiHat baihat : baihatList) {
                        // Kiểm tra và cập nhật giá trị id lớn nhất
                        if (baihat.getIdBaiHat() > nextIdBaiHat) {
                            nextIdBaiHat = baihat.getIdBaiHat();
                        }
                    }
                    // Giá trị nextIdBaiHat sẽ là giá trị lớn nhất + 1 để tạo ra một giá trị mới
                    nextIdBaiHat += 1;

                    // Tạo một tham chiếu đến Firebase Storage
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                    // Upload ảnh bìa và lấy URL
                    StorageReference imageRef = storageRef.child("Bia_Nhac/").child(String.valueOf(nextIdBaiHat));
                    UploadTask imageUploadTask = imageRef.putFile(imgUri);
                    int finalNextIdBaiHat = nextIdBaiHat;
                    imageUploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    if(uri != null) {
                                        mUri[0] = uri.toString();
                                        newBaiHat.setBiaBH(mUri[0]);

                                        // Upload file nhạc và lấy URL
                                        StorageReference musicRef = storageRef.child("File_Audio/").child(String.valueOf(finalNextIdBaiHat));
                                        UploadTask musicUploadTask = musicRef.putFile(audioUri);
                                        musicUploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                musicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uriaudio) {
                                                        if (uriaudio != null) {
                                                            // Đây là đường dẫn của bài hát trên Firebase Storage
                                                            String audioUrl = uriaudio.toString();
                                                            newBaiHat.setLinkBH(audioUrl);

                                                            // Lưu thông tin bài hát vào Realtime Database
                                                            DatabaseReference newBH = baihatReference.child(String.valueOf(finalNextIdBaiHat));
                                                            int idBH = finalNextIdBaiHat;
                                                            newBaiHat.setIdBaiHat(idBH);
                                                            newBH.setValue(newBaiHat);

                                                            // Đảm bảo progressDialog.dismiss() chỉ được gọi khi toàn bộ quá trình đã hoàn tất
                                                            progressDialog.dismiss();
                                                            Toast.makeText(ThemBH_Acitivity.this, "Bài hát đã được thêm thành công", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
                @Override
                public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

                }
            });
        }
    }

}