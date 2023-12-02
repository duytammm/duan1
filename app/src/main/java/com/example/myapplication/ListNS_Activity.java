package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Lst_NS_Adapter;
import com.example.myapplication.DAO.User_DAO;
import com.example.myapplication.model.User;

import java.util.List;

public class ListNS_Activity extends AppCompatActivity {
    private SearchView searchNS;
    private RecyclerView rcvLstNS;
    private Button btXong;
    private User_DAO daoNS;
    private Lst_NS_Adapter nsAdapter;
    private void initView() {
        searchNS = findViewById(R.id.searchNS);
        rcvLstNS = findViewById(R.id.rcvLstNS);
        btXong = findViewById(R.id.btXong);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ns);
        initView();

        btXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        daoNS = new User_DAO(ListNS_Activity.this);
        fillrcvLstNS();
        actSearchView();
    }

    private void fillrcvLstNS() {
        daoNS.getLstCS(new User_DAO.ongetListCS() {
            @Override
            public void ongetSuccess(List<User> lstCS) {
                GridLayoutManager manager = new GridLayoutManager(ListNS_Activity.this,3);
                nsAdapter = new Lst_NS_Adapter(ListNS_Activity.this,lstCS);
                rcvLstNS.setLayoutManager(manager);
                rcvLstNS.setAdapter(nsAdapter);
                nsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void actSearchView() {
        searchNS.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                nsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                nsAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}