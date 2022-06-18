package com.example.adminnetflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.models.response.DataListUserReponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListImageUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<DataListUserReponse> mUserList;

    public ListImageUserAdapter(Context mContext, List<DataListUserReponse> mUserList) {
        this.mContext = mContext;
        this.mUserList = mUserList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_image,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataListUserReponse usera = mUserList.get(0);
        DataListUserReponse userb = mUserList.get(1);
        DataListUserReponse userc = mUserList.get(2);
        String imgUsera = usera.getImage().getUrl();
        String imgUserb = userb.getImage().getUrl();
        String imgUserc = userc.getImage().getUrl();

        Picasso.with(mContext)
                .load(imgUsera).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemUrla);

        Picasso.with(mContext)
                .load(imgUserb).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemUrlb);

        Picasso.with(mContext)
                .load(imgUserc).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemUrlc);


    }


    @Override
    public int getItemCount() {
        if (mUserList != null){
            return mUserList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemUrla;
        private ImageView itemUrlb;
        private ImageView itemUrlc;


        public ItemViewHolder( View itemView) {
            super(itemView);
            itemUrla = itemView.findViewById(R.id.img_a);
            itemUrlb = itemView.findViewById(R.id.img_b);
            itemUrlc = itemView.findViewById(R.id.img_c);
        }
    }

}
