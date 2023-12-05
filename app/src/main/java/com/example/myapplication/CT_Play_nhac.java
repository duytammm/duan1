package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.BaiHat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CT_Play_nhac extends AppCompatActivity {
    private Toolbar tb;
    private ImageView imgDiaNhac;
    //    private ViewPager vpgPlayNhac;
    private TextView timeSong, totaltimeSong;
    private SeekBar seekbarSong;
    private ImageButton ngaunhien, imgBack, imgPlay, imgNext, imgrepeat;
    private Handler handler = new Handler();
    private List<BaiHat> listSongUrls;  // Danh sách các URL của các bài hát
    private int currentSongIndex;   // Chỉ số của bài hát hiện tại
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private void initView() {
        tb = findViewById(R.id.tb);
//        vpgPlayNhac = findViewById(R.id.vpgPlayNhac);
        timeSong = findViewById(R.id.timeSong);
        totaltimeSong = findViewById(R.id.totaltimeSong);
        seekbarSong = findViewById(R.id.seekbarSong);
        ngaunhien = findViewById(R.id.ngaunhien);
        imgDiaNhac = findViewById(R.id.imgDiaNhac);
        imgBack = findViewById(R.id.imgBack);
        imgPlay = findViewById(R.id.imgPlay);
        imgNext = findViewById(R.id.imgNext);
        imgrepeat = findViewById(R.id.imgrepeat);

        setSupportActionBar(tb);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setTitleTextColor(Color.WHITE);
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
        setContentView(R.layout.activity_ct_play_nhac);
        initView();
        clickListener();

        mediaPlayer = new MediaPlayer();

        // Trích xuất ID BaiHat từ intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("baihat_id")) {
            currentSongIndex = intent.getIntExtra("baihat_id", 0);
            fetchSongUrlsFromFirebase(currentSongIndex);
        }

    }

    private void fetchSongUrlsFromFirebase(int currentSongIndex) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("BAIHAT");

        reference.orderByChild("idBaiHat").equalTo(currentSongIndex).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listSongUrls = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        BaiHat baiHat = snapshot.getValue(BaiHat.class);
                        if(baiHat != null) {
                            listSongUrls.add(baiHat);
                            tb.setTitle(baiHat.getTenBH());
                            Glide.with(CT_Play_nhac.this).load(Uri.parse(baiHat.getBiaBH())).error(R.drawable.lofichill).into(imgDiaNhac);

                            // Kiểm tra nếu bài hát hiện tại đang chạy
                            if (mediaPlayer.isPlaying()) {
                                // Dừng bài hát hiện tại trước khi chơi bài hát mới
                                stopMusic();
                            } else {
                                prepareAndPlayMusic(baiHat.getLinkBH());
                            }

                            break;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void clickListener() {
        // Sự kiện thay đổi của SeekBar
        seekbarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    updateTimeTextView(timeSong, progress);
                    updateTimeTextView(totaltimeSong, mediaPlayer.getDuration() - progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Không cần xử lý
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Không cần xử lý
            }
        });

        // Sự kiện click play/pause
        imgPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseMusic();
                } else {
                    playMusic();
                }
            }
        });
    }

    private void playMusic() {
        if (imgPlay != null) {
            if (!mediaPlayer.isPlaying() ) {
                mediaPlayer.start();
                isPlaying = true;
                imgPlay.setImageResource(R.drawable.not_pause);
                handler.postDelayed(updateSeekBar, 1000);
            }
        }
    }

    private void pauseMusic() {
        if (imgPlay != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                isPlaying = false;
                imgPlay.setImageResource(R.drawable.play);
                handler.removeCallbacks(updateSeekBar);
            }
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
            imgPlay.setImageResource(R.drawable.play);
            handler.removeCallbacks(updateSeekBar);
        }
    }

    private void prepareAndPlayMusic(String songUrl) {
        if (songUrl == null) {
            return;
        }

        try {
            mediaPlayer.setDataSource(songUrl);
            mediaPlayer.prepare();
//            mediaPlayer.start();
//            mediaPlayer.stop();

            seekbarSong.setMax(mediaPlayer.getDuration());
            updateTimeTextView(totaltimeSong, mediaPlayer.getDuration());

            handler.postDelayed(updateSeekBar, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && isPlaying) {
                int currentDuration = mediaPlayer.getCurrentPosition();
                int totalDuration = mediaPlayer.getDuration();

                seekbarSong.setMax(totalDuration);
                seekbarSong.setProgress(currentDuration);

                updateTimeTextView(timeSong, currentDuration);
                updateTimeTextView(totaltimeSong, totalDuration);

                handler.postDelayed(this, 1000);
            }
        }
    };

    private void updateTimeTextView(TextView textView, int milliseconds) {
        int seconds = (milliseconds / 1000) % 60;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textView.setText(time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            try {
                stopMusic();
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer = null;
        }
    }

}