package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    private FrameLayout framLayout;
    private BottomNavigationView bottomNagigatiom;
    private Toolbar toolbar;
    private RelativeLayout constrain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
        bottomNagigatiom = findViewById(R.id.bottomNagigatiom);
        framLayout = findViewById(R.id.framLayout);
        constrain = findViewById(R.id.constrain);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_music);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framLayout,new Main_Home())
                .commit();

        bottomNagigatiom.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framLayout, new Main_Home())
                            .commit();
                }
                if (item.getItemId() == R.id.search) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framLayout, new TimKiem_Fragment())
                            .commit();
                }
                if (item.getItemId() == R.id.library) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framLayout, new ThuVien_Fragment())
                            .commit();
                }
                if (item.getItemId() == R.id.account) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framLayout, new TK_QuanLy_fragment())
                            .commit();
                }
                toolbar.setTitle(item.getTitle());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framLayout,new Main_Home())
                    .commit();
            Toast.makeText(this, "Trang chủ", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.all) {
            Toast.makeText(this, "Tất cả", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.nhac) {
            Toast.makeText(this, "Nhạc", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.podcast) {
            Toast.makeText(this, "Podcast và chương trình", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}