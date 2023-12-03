package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ThuVien_NS_Adapter;
import com.example.myapplication.model.BoSuuTap_NS;
import com.example.myapplication.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThuVien_Fragment extends Fragment {
    private ImageView imgNgheGanDay, imgLoc;
    private RecyclerView rcvNgheSi;
    private LinearLayout linearThemNS, linearThemPodcast;
    private List<User> lstUSER ;
    private ThuVien_NS_Adapter nsAdapter;
    private ListNS_Activity listNSActivity = new ListNS_Activity();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference NSyeuthich ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.thuvien_fragment,container,false);
        imgNgheGanDay = v.findViewById(R.id.imgNgheGanDay);
        imgLoc = v.findViewById(R.id.imgLoc);
        rcvNgheSi = v.findViewById(R.id.rcvNgheSi);
        linearThemNS = v.findViewById(R.id.linearThemNS);
        linearThemPodcast = v.findViewById(R.id.linearThemPodcast);

        lstUSER = new ArrayList<>();

        clickListener();

        //ID nguoidung
        SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("DataUser",MODE_PRIVATE);
        String idND = sharedPreferences1.getString("id","");

        fillData(idND);

        return v;
    }

    private void clickListener() {
        linearThemNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListNS_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void fillData(String idND) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BST_NS");
        databaseReference.orderByChild("idND").equalTo(idND)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            for(DataSnapshot bstSnapshot : snapshot.getChildren()) {
                                BoSuuTap_NS boSuuTapNs = bstSnapshot.getValue(BoSuuTap_NS.class);
                                if(boSuuTapNs != null && boSuuTapNs.getIdNS() != null) {
                                    getCS(boSuuTapNs.getIdNS());
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getCS(String idCS) {
        DatabaseReference reference = database.getReference("USER");
        reference.child(idCS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userModel = snapshot.getValue(User.class);
                if (userModel != null) {
                    lstUSER.add(userModel);
                    updateRecyclerView(lstUSER);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi: " + error , Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateRecyclerView(List<User> lstUSER) {
        // Kiểm tra nếu adapter chưa được khởi tạo
        if (nsAdapter == null) {
            nsAdapter = new ThuVien_NS_Adapter(getActivity(), lstUSER);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
            rcvNgheSi.setLayoutManager(manager);
            rcvNgheSi.setAdapter(nsAdapter);
        } else {
            // Adapter đã tồn tại, chỉ cần cập nhật dữ liệu
            nsAdapter.updateData(lstUSER);
        }
    }

}
