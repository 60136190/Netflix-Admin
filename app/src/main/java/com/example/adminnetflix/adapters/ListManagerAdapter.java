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
import com.example.adminnetflix.models.ItemManagerModel;

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
                .inflate(R.layout.item_manager,parent,false);
        return new ListManagerAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemManagerModel itemManagerModel = managerModelList.get(position);
        ((ItemViewHolder)holder).itemTitle.setText(itemManagerModel.getTitle());
        ((ItemViewHolder)holder).itemAmount.setText(itemManagerModel.getAmount());
        ((ItemViewHolder)holder).ctManager.setBackgroundColor(itemManagerModel.getColor());

    }

    @Override
    public int getItemCount() {
        if (managerModelList != null){
            return managerModelList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImg;
        public TextView itemTitle;
        public TextView itemAmount;
        private ConstraintLayout ctManager;

        public ItemViewHolder( View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.img_manager);
            itemTitle = itemView.findViewById(R.id.tv_title_manager);
            itemAmount = itemView.findViewById(R.id.tv_amount);
            ctManager = itemView.findViewById(R.id.ct_manager);
        }
    }
}
