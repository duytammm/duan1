package com.example.myapplication;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Lst_Song_Adapter;
import com.example.myapplication.DAO.BaiHat_DAO;
import com.example.myapplication.model.BaiHat;

import java.util.List;

public class SearchSong_Activity extends AppCompatActivity {
    private Toolbar tbsearchSong;
    private RecyclerView rcvSong;
    private SearchView searchView;
    private Lst_Song_Adapter lstSongAdapter;
    private BaiHat_DAO baiHatDao;
    private void initView() {
        tbsearchSong = findViewById(R.id.tbsearchSong);
        rcvSong = findViewById(R.id.rcvSong);
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
        setContentView(R.layout.activity_search_song);
        initView();

        setSupportActionBar(tbsearchSong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_black);
        getSupportActionBar().setTitle("Bạn muốn nghe gì?");

        baiHatDao = new BaiHat_DAO(this);

        fillAdapter();

    }

    private void fillAdapter() {
        baiHatDao.getAllSong(new BaiHat_DAO.getLstBH() {
            @Override
            public void onSuccess(List<BaiHat> LstbaiHats) {
                lstSongAdapter = new Lst_Song_Adapter(SearchSong_Activity.this,LstbaiHats);
                LinearLayoutManager manager = new LinearLayoutManager(SearchSong_Activity.this);
                rcvSong.setLayoutManager(manager);
                rcvSong.setAdapter(lstSongAdapter);
                lstSongAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_song,menu);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                lstSongAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Khi người dùng thay đổi trong ô tìm kiếm
                lstSongAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }
}