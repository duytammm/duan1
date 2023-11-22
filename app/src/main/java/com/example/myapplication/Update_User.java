package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Update_User extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 71;
    private Uri mUri;
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
        setUserInformation();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            Uri selecteduri = data.getData();
            mUri = selecteduri;
            //                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                imgAvatar.setImageBitmap(bitmap);

            Glide.with(this).load(selecteduri).into(imgAvatar);
        }
    }

    private void uploadImage() {

        if(mUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("Avatar_User/"+ UUID.randomUUID().toString());
            ref.putFile(mUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String name = edtFullName.getText().toString().trim();
                                    String mail = edtEmail.getText().toString().trim();
                                    String matKhau = edtMatKhau.getText().toString().trim();
                                    String sdt = edtSdt.getText().toString().trim();

                                    FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                                    String userID = users.getUid();
                                    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference refs = mDatabase.getReference("USER");

                                    User user = new User(userID,mail,name,sdt,matKhau);
                                    user.setAvatar(uri.toString());
                                    refs.setValue(user, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                            Toast.makeText(Update_User.this, "Update success", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                            Toast.makeText(Update_User.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Update_User.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            return;
        }
        Glide.with(Update_User.this).load(user.getPhotoUrl()).error(R.drawable.avatar_null).into(imgAvatar);
        edtFullName.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());

        edtSdt.setText(user.getPhoneNumber());
    }

}