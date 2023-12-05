package com.example.myapplication;

import static com.example.myapplication.Update_User.MY_REQUEST_CODE;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.QLPLBH_Adapter;
import com.example.myapplication.DAO.PlayList_DAO;
import com.example.myapplication.model.PlayList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class QuanLyPLBH_Activity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 71;
    private Uri imgUri;
    private Toolbar tbQLPL;
    private RecyclerView rcvPL;
    private ImageView imgPL;
    private EditText edtTenPL;
    private FloatingActionButton addPL;
    private PlayList_DAO playListDao;
    private QLPLBH_Adapter adapter;
    private void initView() {
        tbQLPL = findViewById(R.id.tbQLPL);
        rcvPL = findViewById(R.id.rcvPL);
        addPL = findViewById(R.id.addPL);

        setSupportActionBar(tbQLPL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle("Quản lý Playlist");
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
        setContentView(R.layout.activity_quan_ly_plbh);
        initView();

        playListDao = new PlayList_DAO(this);
        fillData();

        addPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder bd = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_pl,null);
        bd.setView(view);

        AlertDialog alert = bd.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alert.setCancelable(false);
        alert.show();

        imgPL = view.findViewById(R.id.imgPL);
        edtTenPL = view.findViewById(R.id.edtTenPL);
        Button btThem = view.findViewById(R.id.btThem);
        Button btHuy = view.findViewById(R.id.btHuy);

        imgPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPl();
            }
        });

        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
    }

    private void uploadPl() {
        final String[] mUri = {""};
        if(imgUri!= null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Đang thêm...");
            progressDialog.show();

            String tenpl = edtTenPL.getText().toString();

            // Tạo một tham chiếu đến Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

            DatabaseReference plReference = FirebaseDatabase.getInstance().getReference("PLAYLIST");
            String idpl = plReference.push().getKey();

            // Upload ảnh bìa và lấy URL
            StorageReference imageRef = storageRef.child("Bia_PlayList/").child(idpl);
            UploadTask imageUploadTask = imageRef.putFile(imgUri);
            imageUploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(uri != null) {
                                String anhbia = uri.toString();

                                PlayList playList = new PlayList(idpl,tenpl,anhbia);
                                plReference.child(idpl).setValue(playList);
                                Toast.makeText(QuanLyPLBH_Activity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                // Đảm bảo progressDialog.dismiss() chỉ được gọi khi toàn bộ quá trình đã hoàn tất
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            });
        }
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
                                Glide.with(QuanLyPLBH_Activity.this).load(imgUri).into(imgPL);
                            } catch (Exception e) {
                                Toast.makeText(QuanLyPLBH_Activity.this, "Lỗi: " + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });

    private void checkPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
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
                if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            Uri selecteduri = data.getData();
            imgUri = selecteduri;
            Glide.with(this).load(selecteduri).into(imgPL);
        }
    }
    private void fillData() {
        playListDao.getPlayList(new PlayList_DAO.getListPlayList() {
            @Override
            public void ongetSuccess(ArrayList<PlayList> ListPlayList) {
                LinearLayoutManager manager = new LinearLayoutManager(QuanLyPLBH_Activity.this);
                adapter = new QLPLBH_Adapter(QuanLyPLBH_Activity.this,ListPlayList);
                rcvPL.setLayoutManager(manager);
                rcvPL.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }
}