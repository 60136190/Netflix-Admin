package com.example.adminnetflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.adminnetflix.R;
import com.example.adminnetflix.models.response.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListUserSubRecentlyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<User> userList;

    public ListUserSubRecentlyAdapter(Context mContext, List<User> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_user_sub_recently, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = userList.get(position);

            String img = user.getImage().getUrl();

            Picasso.with(mContext)
                    .load(img).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).imgUser);


    }


    @Override
    public int getItemCount() {
        if (userList != null) {
            return userList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgUser;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
        }
    }

}
