package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.HoaDon;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HoaDon_Adapter extends RecyclerView.Adapter<HoaDon_Adapter.ViewHolder> {
    private List<HoaDon> lst;
    private Context c;

    public HoaDon_Adapter(List<HoaDon> lst, Context c) {
        this.lst = lst;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDon hoaDon = lst.get(position);
        holder.idhd.setText("ID: " +  hoaDon.getIdHD());
        holder.hoten.setText("" + hoaDon.getHoTenUser());
        holder.ngaylap.setText("Ngày lập: " + hoaDon.getNgayLap());
        holder.time.setText("Thời gian: " + hoaDon.getThoigian());
        String tt = "";
        if(hoaDon.getTrangthai() == 1) {
            tt = "Đã thanh toán";
            holder.trangthai.setText("Trạng thái: " + tt);
        } else {
            tt = "Hết hạn";
            holder.trangthai.setText("Trạng thái: " + tt);
            holder.bt.setVisibility(View.GONE);
        }

        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hoaDon.getTrangthai() == 1) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("HOADON");
                    HoaDon hoaDon1 = new HoaDon(hoaDon.getIdHD(), hoaDon.getHoTenUser(), hoaDon.getMailUser(), hoaDon.getNgayLap(), hoaDon.getThoigian(), hoaDon.getIdUser(), hoaDon.getDongia(),0);
                    reference.child(hoaDon.getIdHD()).setValue(hoaDon1);
                    holder.bt.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lst != null) {
            return lst.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView idhd, hoten, ngaylap, time, trangthai;
        private Button bt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bt = itemView.findViewById(R.id.bt);
            idhd = itemView.findViewById(R.id.idhd);
            hoten = itemView.findViewById(R.id.hoten);
            ngaylap = itemView.findViewById(R.id.ngaylap);
            time = itemView.findViewById(R.id.time);
            trangthai = itemView.findViewById(R.id.trangthai);
        }
    }
}
