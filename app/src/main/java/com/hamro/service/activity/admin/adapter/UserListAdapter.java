package com.hamro.service.activity.admin.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamro.service.R;
import com.hamro.service.activity.ServiceDetailActivity;
import com.hamro.service.activity.admin.dao.UserDetail;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ImageViewHolder> {


    private final Context context;
    private final ArrayList<UserDetail> users;
    ClipboardManager clipboardManager;

    public UserListAdapter(Context context, ArrayList<UserDetail> users) {
        this.context = context;
        this.users = users;
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @NonNull
    @Override
    public UserListAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_row,parent,false);
        view.setOnClickListener(v -> {

            context.startActivity(new Intent(context, ServiceDetailActivity.class));
        });
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ImageViewHolder holder, int position) {
        holder.userText.setText(users.get(position).toString());
        holder.userText.setOnClickListener(v -> {
            ClipData clipData = ClipData.newPlainText("label",users.get(position).getPhone());
            if(clipData !=null){
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context,"Phone number is copied",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView userText;
        public ImageViewHolder(@NonNull View itemView) {

            super(itemView);
            this.userText = itemView.findViewById(R.id.user);

        }
    }//
}
