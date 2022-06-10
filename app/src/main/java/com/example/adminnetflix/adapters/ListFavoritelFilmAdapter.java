package com.example.adminnetflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.models.response.favourite.Favourite;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListFavoritelFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<Favourite> mFavoriteList;

    public ListFavoritelFilmAdapter(Context mContext, List<Favourite> mFavoriteList) {
        this.mContext = mContext;
        this.mFavoriteList = mFavoriteList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_favorite, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Favourite favorite = mFavoriteList.get(position);
        if ((favorite.getFilm() != null) && (favorite.getUser() != null)) {
            String idUser = favorite.getUser().getId();
            String idShared = StoreUtil.get(mContext, Contants.idUser);
            String img = favorite.getFilm().getImageFilm().getUrl();
            String title = favorite.getFilm().getTitle();
            if ((idUser.equals(idShared))) {
                ((ItemViewHolder)holder).ctFavourite.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(img).error(R.drawable.backgroundslider).fit().centerInside()
                        .into(((ItemViewHolder) holder).itemImage);
                ((ItemViewHolder)holder).itemTitle.setText(title);
            }
            else {
                ((ItemViewHolder)holder).ctFavourite.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }
        else {
            ((ItemViewHolder)holder).ctFavourite.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    @Override
    public int getItemCount() {
        if (mFavoriteList != null) {
            return mFavoriteList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        FrameLayout ctFavourite;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.img_film);
            itemTitle = itemView.findViewById(R.id.tv_title);
            ctFavourite = itemView.findViewById(R.id.ct_favorite);
        }
    }
}
