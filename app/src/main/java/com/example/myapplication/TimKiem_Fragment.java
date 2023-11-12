package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.Adapter.TK_PlayList_Adapter;
import com.example.myapplication.Adapter.TimKiem_TabLayout_Adapter;
import com.example.myapplication.model.PlayList;
import com.google.android.material.search.SearchBar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimKiem_Fragment extends Fragment {
    private SearchBar svTimKiem;
    private ImageView imgLofi,imgPodcast, imgMelody;
    private RecyclerView rcvPlayListTK;
    private TabLayout tabLayout;
    private ViewPager2 vpg2;
    private TK_PlayList_Adapter adapter;
    private List<PlayList> lstPL;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.timkiem_fragment,container,false);
        svTimKiem = v.findViewById(R.id.svTimKiem);
        rcvPlayListTK = v.findViewById(R.id.rcvPlayListTK);
//        imgMelody = v.findViewById(R.id.imgMelody);
//        imgLofi = v.findViewById(R.id.imgLofi);
//        imgPodcast = v.findViewById(R.id.imgPodcast);
        tabLayout = v.findViewById(R.id.tablayout);
        vpg2 = v.findViewById(R.id.vpg2);

        DoPlayList();
        TabLayout();

        return v;
    }

    public void TabLayout() {
        TimKiem_TabLayout_Adapter adapter = new TimKiem_TabLayout_Adapter(this);
        vpg2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, vpg2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0:{
                        //Đổi màu cho tab
                        tabLayout.setSelectedTabIndicatorColor(R.drawable.custom_tab_indicator);
                        tab.setText("Nổi bật");
                        break;
                    }
                    case 1:{
                        //Đổi màu cho tab
                        tabLayout.setSelectedTabIndicatorColor(R.drawable.custom_tab_indicator);
                        tab.setText("Trending Music");
                        break;
                    }
                }
            }
        }).attach();

        //Set màu background cho tab
        /**tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Tab được chọn
                tab.view.setBackgroundColor(Color.rgb(1,1,1));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Tab không được chọn
                tab.view.setBackgroundColor(Color.GRAY);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Tab được chọn lại
            }
        }); */
    }

    private void DoPlayList() {
        lstPL = new ArrayList<>();
        adapter = new TK_PlayList_Adapter(lstPL,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        rcvPlayListTK.setLayoutManager(manager);
        rcvPlayListTK.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("PLAYLIST");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PlayList playList = dataSnapshot.getValue(PlayList.class);
                    lstPL.add((playList));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
