package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adapter.SlideShow_Adapter;
import com.example.myapplication.Adapter.TC_NS_Adapter;
import com.example.myapplication.Adapter.TC_PlayList_Adapter;
import com.example.myapplication.DAO.PlayList_DAO;
import com.example.myapplication.DAO.User_DAO;
import com.example.myapplication.model.PlayList;
import com.example.myapplication.model.SlideShow;
import com.example.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class Main_Home extends Fragment {
    private RecyclerView rcvNS, rcvPlaylist;
    //Slide show sử dụng sự hỗ trợ của hai thư viện bên ngoài: CircleIndicator và glide của git
    private ViewPager viewPager;
    private CircleIndicator cirCle;
    private List<SlideShow> lst;
    private PlayList_DAO pldao;
    private User_DAO dao;
    private TC_PlayList_Adapter adapterPL;
    private TC_NS_Adapter adapterNS;
    private final int AUTO_SCROLL_DELAY = 3000; // Thời gian chuyển đổi giữa các trang (3 giây)
    private Handler handler;
    private Runnable runnable;

    @Override
    public void onResume() {
        super.onResume();
        startAutoSlider();
    }

    @Override
    public void onPause() {
        stopAutoSlider();
        super.onPause();
    }

    private void startAutoSlider() {
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItems = viewPager.getAdapter() != null ? viewPager.getAdapter().getCount() : 0;
                if (currentItem < totalItems - 1) {
                    viewPager.setCurrentItem(currentItem + 1);
                } else {
                    viewPager.setCurrentItem(0);
                }
                handler.postDelayed(this, AUTO_SCROLL_DELAY);
            }
        };
        handler.postDelayed(runnable, AUTO_SCROLL_DELAY);
    }

    private void stopAutoSlider() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_home,container,false);
        viewPager = view.findViewById(R.id.viewPager);
        cirCle = view.findViewById(R.id.cirCle);
        rcvPlaylist = view.findViewById(R.id.rcvPlaylist);
        rcvNS = view.findViewById(R.id.rcvNS);

        pldao = new PlayList_DAO(getContext());
        dao = new User_DAO(getContext());

        //SLIDESHOW
        SlideShow_Adapter adapter = new SlideShow_Adapter(getContext(),lst());
        viewPager.setAdapter(adapter);
        cirCle.setViewPager(viewPager);
        adapter.registerDataSetObserver(cirCle.getDataSetObserver());

        setAdapter();
        clickSlideshow();

        return view;
    }
    private List<SlideShow> lst() {
        lst = new ArrayList<>();
        lst.add(new SlideShow(1,R.drawable.trangchu_banner1));
        lst.add(new SlideShow(2,R.drawable.trangchu_banner2));
        lst.add(new SlideShow(3,R.drawable.trangchu_banner3));
        lst.add(new SlideShow(4,R.drawable.trangchu_banner4));
        lst.add(new SlideShow(5,R.drawable.trangchu_banner5));
        return lst;
    }

    private void setAdapter() {
        pldao.getPlayList(new PlayList_DAO.getListPlayList() {
            @Override
            public void ongetSuccess(ArrayList<PlayList> ListPlayList) {
                LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                rcvPlaylist.setLayoutManager(manager);
                adapterPL = new TC_PlayList_Adapter(ListPlayList,getActivity());
                rcvPlaylist.setAdapter(adapterPL);
                adapterPL.notifyDataSetChanged();
            }
        });

        dao.getLstCS(new User_DAO.ongetListCS() {
            @Override
            public void ongetSuccess(List<User> lstCS) {
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                adapterNS = new TC_NS_Adapter(lstCS,getActivity());
                rcvNS.setLayoutManager(manager);
                rcvNS.setAdapter(adapterNS);
                adapterNS.notifyDataSetChanged();
            }
        });
    }

    private void clickSlideshow() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Không cần xử lý
            }

            @Override
            public void onPageSelected(int position) {
                // Xử lý khi một trang được chọn
                // Ví dụ: Hiển thị thông báo Toast với vị trí của trang được chọn
                //Toast.makeText(getContext(), "Trang được chọn: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Không cần xử lý
            }
        });

    }
}
