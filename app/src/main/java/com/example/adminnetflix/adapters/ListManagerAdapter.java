package com.example.adminnetflix.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.adminnetflix.activities.manage.FavoriteActivity;
import com.example.adminnetflix.activities.manage.FeedbackActivity;
import com.example.adminnetflix.activities.manage.ModeOfPaymentActivity;
import com.example.adminnetflix.activities.manage.RatingActivity;
import com.example.adminnetflix.activities.manage.UserActivity;
import com.example.adminnetflix.models.ItemManagerModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListManagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<ItemManagerModel> managerModelList;

    public ListManagerAdapter(Context mContext, List<ItemManagerModel> managerModelList) {
        this.mContext = mContext;
        this.managerModelList = managerModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manager, parent, false);
        return new ListManagerAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemManagerModel itemManagerModel = managerModelList.get(position);
        int a = position;
        ((ItemViewHolder) holder).itemTitle.setText(itemManagerModel.getTitle());
        String img = itemManagerModel.getImage();
        Picasso.with(mContext)
                .load(img).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemImg);
        ((ItemViewHolder) holder).ctManager.setBackgroundResource(itemManagerModel.getColor());

        ((ItemViewHolder) holder).ctManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                switch (a) {
//                    case 0:
//                        Intent user = new Intent(mContext, UserActivity.class);
//                        mContext.startActivity(user);
//                    case 1:
//                        Intent admin = new Intent(mContext, AdminActivity.class);
//                        mContext.startActivity(admin);
//                    case 2:
//                        Intent director = new Intent(mContext, DirectorActivity.class);
//                        mContext.startActivity(director);
//                    case 3:
//                        Intent rating = new Intent(mContext, RatingActivity.class);
//                        mContext.startActivity(rating);
//                    case 4:
//                        Intent feedback = new Intent(mContext, FeedbackActivity.class);
//                        mContext.startActivity(feedback);
//                    case 5:
//                        Intent mode = new Intent(mContext, ModeOfPaymentActivity.class);
//                        mContext.startActivity(mode);
//                    case 6:
//                        Intent cate = new Intent(mContext, CategoryActivity.class);
//                        mContext.startActivity(cate);
//                    case 7:
//                        Intent comment = new Intent(mContext, CommentDeletedActivity.class);
//                        mContext.startActivity(comment);
//                    case 8:
//                        Intent fa = new Intent(mContext, FavoriteActivity.class);
//                        mContext.startActivity(fa);
//                }

                if (a==0){
                    Intent intent = new Intent(mContext,UserActivity.class);
                    mContext.startActivity(intent);
                }

                if (a == 1) {
                    Intent intent = new Intent(mContext,AdminActivity.class);
                    mContext.startActivity(intent);
                }
                if (a == 2) {
                    Intent intent = new Intent(mContext,DirectorActivity.class);
                    mContext.startActivity(intent);
                }
                if (a == 3) {
                    Intent intent = new Intent(mContext,RatingActivity.class);
                    mContext.startActivity(intent);
                }
                if (a == 4) {
                    Intent intent = new Intent(mContext,FeedbackActivity.class);
                    mContext.startActivity(intent);
                }
                if (a == 5) {
                    Intent intent = new Intent(mContext,ModeOfPaymentActivity.class);
                    mContext.startActivity(intent);
                }
                if (a == 6) {
                    Intent intent = new Intent(mContext,CategoryActivity.class);
                    mContext.startActivity(intent);
                }
                if (a == 7) {
                    Intent intent = new Intent(mContext,CommentDeletedActivity.class);
                    mContext.startActivity(intent);
                }
                if (a == 8) {
                    Intent intent = new Intent(mContext, CustomerUncheckActivity.class);
                    mContext.startActivity(intent);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        if (managerModelList != null) {
            return managerModelList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImg;
        public TextView itemTitle;
        private ConstraintLayout ctManager;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.img_manager);
            itemTitle = itemView.findViewById(R.id.tv_title_manager);
            ctManager = itemView.findViewById(R.id.ct_manager);
        }
    }
}
