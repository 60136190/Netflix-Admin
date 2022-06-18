package com.example.adminnetflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListCustomerUncheckAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<User> mUserList;

    public ListCustomerUncheckAdapter(Context mContext, List<User> mUserList) {
        this.mContext = mContext;
        this.mUserList = mUserList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_customer_uncheck,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = mUserList.get(position);
        String fullName = user.getFullname();
        String imgUser = user.getImage().getUrl();

        ((ItemViewHolder) holder).itemFullname.setText(fullName);
        Picasso.with(mContext)
                .load(imgUser).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemUrl);

    }


    @Override
    public int getItemCount() {
        if (mUserList != null){
            return mUserList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemUrl;
        private TextView itemFullname;

        public ItemViewHolder( View itemView) {
            super(itemView);
            itemUrl = itemView.findViewById(R.id.img_user);
            itemFullname = itemView.findViewById(R.id.tv_name_of_user);
        }
    }

}
