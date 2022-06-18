package com.example.adminnetflix.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.adminnetflix.R;
import com.example.adminnetflix.models.response.Director;
import com.example.adminnetflix.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListDirectorCreateFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext1;
    List<Director> mDirectorList;
    ArrayList<String> listId = new ArrayList<>();

    public ListDirectorCreateFilmAdapter(Context mContext, List<Director> mDirectorList) {
        this.mContext1 = mContext;
        this.mDirectorList = mDirectorList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_director_create_film,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Director director = mDirectorList.get(position);
        String title = director.getName();
        String imgDirector = director.getImage().getUrl();

       (((ItemViewHolder)holder).rdbChoose).setOnCheckedChangeListener((buttonView, isChecked) -> {
           if ((((ItemViewHolder)holder).rdbChoose).isChecked()) {
               listId.add(director.getId());
               Log.i("ListID",listId.toString());
               StoreUtil.writeListInPref(mContext1,listId);
           }else{
               listId.remove(director.getId());
               Log.i("ListID",listId.toString());
               StoreUtil.writeListInPref(mContext1,listId);
           }
       });


        ((ItemViewHolder) holder).itemNameOfDirector.setText(title);
        Picasso.with(mContext1)
                .load(imgDirector).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemUrl);

    }

    @Override
    public int getItemCount() {
        if (mDirectorList != null){
            return mDirectorList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemUrl;
        private TextView itemNameOfDirector;
        private CheckBox rdbChoose;


        public ItemViewHolder( View itemView) {
            super(itemView);
            itemUrl = itemView.findViewById(R.id.img_director);
            itemNameOfDirector = itemView.findViewById(R.id.tv_name_of_director);
            rdbChoose = itemView.findViewById(R.id.rdb_choose);
        }
    }

}
