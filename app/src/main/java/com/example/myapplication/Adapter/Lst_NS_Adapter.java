package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;

public class Lst_NS_Adapter extends RecyclerView.Adapter<Lst_NS_Adapter.ViewHolder> implements Filterable {
    private Context c;
    private List<User> lstNS = new ArrayList<>();
    private List<User> lstSearch = new ArrayList<>();


    public Lst_NS_Adapter(Context c, List<User> lstNS) {
        this.c = c;
        this.lstNS = lstNS;
        this.lstSearch = lstNS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((Activity)c).getLayoutInflater().inflate(R.layout.item_lst_ns,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User nghesi = lstNS.get(position);
        holder.tenNS.setText(nghesi.getHotenUser());
        if(nghesi.getAvatar() != null) {
            Glide.with(c).load(Uri.parse(nghesi.getAvatar())).error(R.drawable.avatar_null).into(holder.imgAvatar);
        } else {
            Glide.with(c).load(R.drawable.avatar_null).into(holder.imgAvatar);
        }

    }

    @Override
    public int getItemCount() {
        if(lstNS != null) {
            return lstNS.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tenNS;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tenNS = itemView.findViewById(R.id.tenNS);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()) {
                    lstNS = lstSearch;
                } else {
                    List<User> lsUsers = new ArrayList<>();
                    for(User user : lstSearch) {
                        if(user.getHotenUser().toLowerCase().contains(strSearch.toLowerCase())) {
                            lsUsers.add(user);
                        }
                    }
                    lstNS = lsUsers;
                }
                FilterResults results = new FilterResults();
                results.values = lstNS;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lstNS = (List<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
