package com.hamro.service.activity.admin.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hamro.service.R;
import com.hamro.service.activity.ServiceDetailActivity;
import com.hamro.service.activity.admin.dao.UserDetail;

import java.util.ArrayList;

public class HomeImagesAdapter extends RecyclerView.Adapter<HomeImagesAdapter.ImageViewHolder> {


    private final Context context;
    private final ArrayList<String> urls;
    ClipboardManager clipboardManager;

    public HomeImagesAdapter(Context context, ArrayList<String> urls) {
        this.context = context;
        this.urls = urls;
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @NonNull
    @Override
    public HomeImagesAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_list_row,parent,false);
        view.setOnClickListener(v -> {

            context.startActivity(new Intent(context, ServiceDetailActivity.class));
        });
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeImagesAdapter.ImageViewHolder holder, int position) {
        Glide.with(context)
                .load(urls.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ImageViewHolder(@NonNull View itemView) {

            super(itemView);
            image = itemView.findViewById(R.id.image);

        }
    }//
}
