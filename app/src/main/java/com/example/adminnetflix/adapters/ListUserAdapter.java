package com.example.adminnetflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.models.response.DataListUserReponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<DataListUserReponse> mUserList;

    public ListUserAdapter(Context mContext, List<DataListUserReponse> mUserList) {
        this.mContext = mContext;
        this.mUserList = mUserList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_user,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataListUserReponse user = mUserList.get(position);
        String fullName = user.getFullname();
        String imgUser = user.getImage().getUrl();

        ((ItemViewHolder) holder).itemFullname.setText(fullName);
        Picasso.with(mContext)
                .load(imgUser).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemUrl);

//        ((ItemViewHolder) holder).imgPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(mContext, DetailFilmActivity.class);
//                String strName = favorite.getFilm().getId();
//                i.putExtra("Id_film", strName);
//                mContext.startActivity(i);
//
//            }
//        });

    }


    @Override
    public int getItemCount() {
        if (mUserList != null){
            return mUserList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemUrl;
        public TextView itemFullname;
        public ConstraintLayout ctListUser;

        public ItemViewHolder( View itemView) {
            super(itemView);
            itemUrl = itemView.findViewById(R.id.img_user);
            itemFullname = itemView.findViewById(R.id.tv_name_of_user);
            ctListUser = itemView.findViewById(R.id.ct_list_user);
        }
    }

}
