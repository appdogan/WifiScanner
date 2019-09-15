package com.example.onurdogan.wifiscanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    int[] imageArray = {
            R.drawable.excellent,
            R.drawable.good,
            R.drawable.fair,
            R.drawable.weak
    };

    private ArrayList<WifiValues> wifiList;

    public Adapter(ArrayList<WifiValues> wifiList) {
        this.wifiList = wifiList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cell,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {

        holder.wifiName.setText(wifiList.get(position).getWifiName());
        holder.wifiMac.setText(wifiList.get(position).getWifiMac());
        holder.ımgWifi.setImageResource(imageArray[wifiList.get(position).getWifiStrenght()]);
    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView wifiName;
        private CardView card;
        private TextView wifiMac;
        private ImageView ımgWifi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            wifiName = itemView.findViewById(R.id.wifiname);
            card = itemView.findViewById(R.id.cardView);
            wifiMac = itemView.findViewById(R.id.wifiMac);
            ımgWifi = itemView.findViewById(R.id.imageStrength);
        }
    }
}
