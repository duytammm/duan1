package com.example.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Update_User extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 71;
    public static final int MY_REQUEST_CODE = 10;
    private Uri mUri;
    private Bitmap bitmap;
    private Toolbar tbUpdate;
    private ImageView imgAvatar;
    private EditText edtFullName, edtEmail, edtMatKhau, edtSdt;
    private Button btUpdate;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    private void initUI(){
        tbUpdate = findViewById(R.id.tbUpdate);
        imgAvatar = findViewById(R.id.imgAvatar);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtSdt = findViewById(R.id.edtSdt);
        btUpdate = findViewById(R.id.btUpdate);
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
        setContentView(R.layout.activity_update_user);
        initUI();

        setSupportActionBar(tbUpdate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_music);
        getSupportActionBar().setTitle("Cập nhật tài khoản");
//        setUserInformation();
        setUserInfor();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadInformation();
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    private void checkPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Vui lòng mở quyền!!", Toast.LENGTH_SHORT).show();
            }
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
                                mUri = uri;
                                Glide.with(Update_User.this).load(mUri).into(imgAvatar);
                            } catch (Exception e) {
                                Toast.makeText(Update_User.this, "Lỗi: " + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            Uri selecteduri = data.getData();
            mUri = selecteduri;
            Glide.with(this).load(selecteduri).into(imgAvatar);
        }
    }

    private void uploadInformation() {
        if (mUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String name = edtFullName.getText().toString().trim();
            String mail = edtEmail.getText().toString().trim();
            String matKhau = edtMatKhau.getText().toString().trim();
            String sdt = edtSdt.getText().toString().trim();

            StorageReference ref = storageReference.child("Avatar_User/" + mail).child(mail);

            ref.putFile(mUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        ref.getDownloadUrl().addOnSuccessListener(uri -> {
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("USER");

                            Query query = userRef.orderByChild("email").equalTo(mail);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                        User user = userSnapshot.getValue(User.class);
                                        if (user != null) {
                                            user.setEmail(mail);
                                            user.setMatKhau(matKhau);
                                            user.setHotenUser(name);
                                            user.setSdt(sdt);
                                            user.setAvatar(uri.toString());

                                            userRef.child(userSnapshot.getKey()).setValue(user, (error, ref1) -> {
                                                if (error == null) {
                                                    Toast.makeText(Update_User.this, "Update success", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(Update_User.this, "Update failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(Update_User.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        });
                        Toast.makeText(Update_User.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(Update_User.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    });
        }
    }

    public void setUserInfor() {
        SharedPreferences inforSharedPreferences = getSharedPreferences("DataUser",MODE_PRIVATE);
        String mail = inforSharedPreferences.getString("email","");

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Avatar_User").child(mail);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                // Lay link anh
                String imageUrl = downloadUrl.toString();
                Toast.makeText(Update_User.this, "Loi: " + imageUrl, Toast.LENGTH_SHORT).show();
                // load len
                Glide.with(Update_User.this).load(imageUrl).error(R.drawable.avatar_null).into(imgAvatar);
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
                        String sdt = user.getSdt();
                        String mk = user.getMatKhau();
                        if(mail.equals(email)) {
                            edtFullName.setText(hoTen);
                            edtEmail.setText(email);
                            edtSdt.setText(sdt);
                            edtMatKhau.setText(mk);
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_User.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }




}