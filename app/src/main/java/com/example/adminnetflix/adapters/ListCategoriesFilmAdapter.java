package com.example.adminnetflix.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.activities.DetailUserActivity;
import com.example.adminnetflix.models.response.Category;
import com.example.adminnetflix.models.response.DataListUserReponse;
import com.example.adminnetflix.models.response.ListCategories;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListCategoriesFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Category> mCategoriesList;

    public ListCategoriesFilmAdapter(Context mContext, List<Category> mCategoriesList) {
        this.mContext = mContext;
        this.mCategoriesList = mCategoriesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_categories_film,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Category user = mCategoriesList.get(position);
        String title = user.getName();
//        String imgUser = user.getImage().getUrl();

        ((ItemViewHolder) holder).itemTitle.setText(title);
        ((ItemViewHolder) holder).itemNo.setText(String.valueOf(position+1));
//        Picasso.with(mContext)
//                .load(imgUser).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemUrl);
//
//        ((ItemViewHolder) holder).ctListUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(mContext, DetailUserActivity.class);
//                i.putExtra("Id_user", idUser);
//                mContext.startActivity(i);
//
//            }
//        });


    }


    @Override
    public int getItemCount() {
        if (mCategoriesList != null){
            return mCategoriesList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemUrl;
        private TextView itemTitle;
        private TextView itemNo;
        private ConstraintLayout ctListCategoriesFilm;
        private LinearLayout lnDeleteCategory;


        public ItemViewHolder( View itemView) {
            super(itemView);
            itemUrl = itemView.findViewById(R.id.img_category);
            itemTitle = itemView.findViewById(R.id.tv_title_category);
            itemNo = itemView.findViewById(R.id.tv_no);
            ctListCategoriesFilm = itemView.findViewById(R.id.ct_list_categories_film);
            lnDeleteCategory = itemView.findViewById(R.id.ln_delete);
        }
    }

}
