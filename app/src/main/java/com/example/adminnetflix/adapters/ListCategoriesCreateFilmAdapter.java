package com.example.adminnetflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.models.response.Category;
import com.example.adminnetflix.utils.StoreUtil;

import java.util.ArrayList;
import java.util.List;

public class ListCategoriesCreateFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Category> mCategoriesList;
    ArrayList<String> listIdCategory = new ArrayList<>();

    public ListCategoriesCreateFilmAdapter(Context mContext, List<Category> mCategoriesList) {
        this.mContext = mContext;
        this.mCategoriesList = mCategoriesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_categories_create_film,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Category category = mCategoriesList.get(position);
        String title = category.getName();

        ((ItemViewHolder) holder).itemTitle.setText(title);
        (((ItemViewHolder)holder).rdbChoose).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if ((((ItemViewHolder)holder).rdbChoose).isChecked()) {
                listIdCategory.add(category.getId());
                StoreUtil.writeListCategoryPref(mContext,listIdCategory);

            }else{
                listIdCategory.remove(category.getId());
                StoreUtil.writeListCategoryPref(mContext,listIdCategory);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (mCategoriesList != null){
            return mCategoriesList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        CheckBox rdbChoose;

        public ItemViewHolder( View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.tv_title_category);
            rdbChoose = itemView.findViewById(R.id.rdb_choose_category);

        }
    }

}
