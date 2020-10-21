package com.zayano.testcermati.presentation.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zayano.testcermati.R;
import com.zayano.testcermati.data.model.User;
import com.zayano.testcermati.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> usersList = new ArrayList<>();

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public MainActivityAdapter() {
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_item_layout, parent, false);
        return new MainActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MainActivityViewHolder) holder).bind(usersList.get(position));
    }


    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void addNewData(List<User> usersList) {
        this.usersList.clear();
        this.usersList.addAll(usersList);
        this.notifyDataSetChanged();
    }

    public void addData(List<User> usersList) {
        this.usersList.addAll(usersList);
        this.notifyDataSetChanged();
    }

    public User getData(int position) {
        return usersList.get(position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class MainActivityViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivUserAvatar;

        public MainActivityViewHolder(View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tv_user_name);
            ivUserAvatar = itemView.findViewById(R.id.iv_user);

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView, position);
                    }
                }
            });
        }

        public void bind(User user) {
            tvUsername.setText(user.getLogin());
            ImageLoader.setImageUrl(ivUserAvatar, user.getAvatarUrl());
        }
    }


}
