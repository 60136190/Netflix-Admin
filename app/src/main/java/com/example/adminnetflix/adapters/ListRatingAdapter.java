package com.example.adminnetflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.models.response.Director;
import com.example.adminnetflix.models.response.Rating;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListRatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Rating> mRatingList;

    public ListRatingAdapter(Context mContext, List<Rating> mRatingList) {
        this.mContext = mContext;
        this.mRatingList = mRatingList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rating,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Rating rating  = mRatingList.get(position);
        if ((rating.getUser()!=null) && (rating.getFilm()!=null)){
            ((ItemViewHolder)holder).cardView.setVisibility(View.VISIBLE);
            String a = rating.getUser().getFullname();
            ((ItemViewHolder) holder).itemNameOfUser.setText(a);
            ((ItemViewHolder) holder).itemNumRate.setText(String.valueOf(rating.getScore()));
            ((ItemViewHolder) holder).itemTitleFilm.setText(rating.getFilm().getTitle());
            Picasso.with(mContext)
                    .load(rating.getUser().getImage().getUrl()).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemImgUser);
        }else {
            ((ItemViewHolder)holder).cardView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

        }
    }


    @Override
    public int getItemCount() {
        if (mRatingList != null){
            return mRatingList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImgUser;
        private TextView itemNameOfUser;
        private TextView itemTitleFilm;
        private TextView itemNumRate;
        CardView cardView;

        public ItemViewHolder( View itemView) {
            super(itemView);
            itemImgUser = itemView.findViewById(R.id.img_user);
            itemNameOfUser = itemView.findViewById(R.id.tv_name_of_user);
            itemTitleFilm = itemView.findViewById(R.id.tv_title_film);
            itemNumRate = itemView.findViewById(R.id.tv_num_rate);
            cardView = itemView.findViewById(R.id.cv_rating);
        }
    }

}
