package com.example.adminnetflix.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.manage.AdminActivity;
import com.example.adminnetflix.activities.manage.CategoryActivity;
import com.example.adminnetflix.activities.manage.CommentDeletedActivity;
import com.example.adminnetflix.activities.manage.CustomerUncheckActivity;
import com.example.adminnetflix.activities.manage.DirectorActivity;
import com.example.adminnetflix.activities.manage.FeedbackActivity;
import com.example.adminnetflix.activities.manage.ModeOfPaymentActivity;
import com.example.adminnetflix.activities.manage.RatingActivity;
import com.example.adminnetflix.activities.manage.UserActivity;
import com.example.adminnetflix.models.ItemManageStatistical;
import com.example.adminnetflix.models.ItemManagerModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListManagerStatisticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<ItemManageStatistical> itemManageStatisticals;

    public ListManagerStatisticalAdapter(Context mContext, List<ItemManageStatistical> itemManageStatisticals) {
        this.mContext = mContext;
        this.itemManageStatisticals = itemManageStatisticals;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manage_statistical, parent, false);
        return new ListManagerStatisticalAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemManageStatistical itemManagerModel = itemManageStatisticals.get(position);

        String img = itemManagerModel.getImage();
        ((ItemViewHolder) holder).tvTitle.setText(itemManagerModel.getTitle());
        ((ItemViewHolder) holder).tvAmount.setText(String.valueOf(itemManagerModel.getAmount()));
        ((ItemViewHolder) holder).ctManageStatistical.setBackgroundResource(itemManagerModel.getColor());
        Picasso.with(mContext)
                .load(img).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).imgItem);
    }

    @Override
    public int getItemCount() {
        if (itemManageStatisticals != null) {
            return itemManageStatisticals.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAmount;
        ConstraintLayout ctManageStatistical;
        ImageView imgItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            ctManageStatistical = itemView.findViewById(R.id.ct_item_manage_statistical);
            imgItem = itemView.findViewById(R.id.img_item_statistical);
        }
    }
}
