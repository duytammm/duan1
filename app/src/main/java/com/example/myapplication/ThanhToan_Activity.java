package com.example.myapplication;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.HoaDon;
import com.example.myapplication.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ThanhToan_Activity extends AppCompatActivity {
    private Toolbar tbThanhToan;
    private TextView tvHoten, tvMail, tvNLHD, tvTime;
    private Button btThanhToan;
    private ImageView imgAvatar;
    private void initView() {
        tbThanhToan = findViewById(R.id.tbThanhToan);
        tvHoten = findViewById(R.id.tvHoten);
        tvMail = findViewById(R.id.tvMail);
        tvNLHD = findViewById(R.id.tvNLHD);
        tvTime = findViewById(R.id.tvTime);
        btThanhToan = findViewById(R.id.btThanhToan);
        imgAvatar = findViewById(R.id.imgAvatar);

        setSupportActionBar(tbThanhToan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle("Thanh toán");
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
        setContentView(R.layout.activity_thanh_toan);
        initView();

        // Lấy ngày giờ hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String currentDate = day + "/" + month + "/" + year;
        tvNLHD.setText(currentDate);
        //Lấy giờ
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String currentTime = hour + ":" + minute + ":" + second;
        tvTime.setText(currentTime);

        SharedPreferences sharedPreferences = getSharedPreferences("DataUser",MODE_PRIVATE);
        String idUser = sharedPreferences.getString("id","");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("USER");
        reference.child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    tvHoten.setText(user.getHotenUser());
                    tvMail.setText(user.getEmail());
                    Glide.with(ThanhToan_Activity.this).load(Uri.parse(user.getAvatar())).error(R.drawable.avatar).into(imgAvatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhtoan();
            }
        });
    }

    private void thanhtoan() {
        // Lấy ngày giờ hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String currentDate = day + "/" + month + "/" + year;
        tvNLHD.setText(currentDate);
        //Lấy giờ
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String currentTime = hour + ":" + minute + ":" + second;
        tvTime.setText(currentTime);

        SharedPreferences sharedPreferences = getSharedPreferences("DataUser",MODE_PRIVATE);
        String idUser = sharedPreferences.getString("id","");
        String hoten = tvHoten.getText().toString();
        String mail = tvMail.getText().toString();

        DatabaseReference hdReference = FirebaseDatabase.getInstance().getReference("HOADON");
        String idHD = hdReference.push().getKey();
        //String idHD, String hoTenUser, String mailUser, String ngayLap, String thoigian, String idUser, int dongia
        HoaDon hoaDon = new HoaDon(idHD,hoten,mail,currentDate,currentTime,idUser,49000,1);
        if(hoaDon!= null) {
            hdReference.child(idHD).setValue(hoaDon);
            Toast.makeText(this, "Thanh toán thành công.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}