package com.hamro.service.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hamro.service.R;
import com.hamro.service.activity.ServiceDetailActivity;
import com.hamro.service.data.HomeData;
import com.hamro.service.data.ServiceData;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ImageViewHolder> {


    private final HomeData data;
    private final Context context;
    private final boolean isEnglish;

    public ServiceListAdapter(Context context, HomeData data,boolean isEnglish) {
        this.context = context;
        this.data = data;
        this.isEnglish = isEnglish;
    }

    @NonNull
    @Override
    public ServiceListAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_llist_row,parent,false);
        view.setOnClickListener(v -> {
            Intent intent = new Intent(context, ServiceDetailActivity.class);
//            intent.putExtra("service",)
//            context.startActivity();
        });
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListAdapter.ImageViewHolder holder, int position) {
        ServiceData service = data.getServices().get(position);
        if(isEnglish)
            holder.service_name.setText(service.getName());
        else{
            if(service.getNameNepali().length()>2)
                holder.service_name.setText(service.getNameNepali());
            else
                holder.service_name.setText(service.getName());

        }
        holder.service_icon.setImageDrawable(ContextCompat.getDrawable(context,service.getLogo()));
        holder.card.setOnClickListener(v -> {
            Intent intent = new Intent(context, ServiceDetailActivity.class);
            intent.putExtra("service",position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.getServices().size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView service_name;
        ImageView service_icon;
        CardView card;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.service_name);
            service_icon = itemView.findViewById(R.id.service_icon);
            card = itemView.findViewById(R.id.card);
        }
    }//
}
