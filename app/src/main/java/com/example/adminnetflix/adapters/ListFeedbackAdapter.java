package com.example.adminnetflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminnetflix.R;
import com.example.adminnetflix.models.response.Feedback;
import com.example.adminnetflix.models.response.Rating;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListFeedbackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Feedback> mFeedbackList;

    public ListFeedbackAdapter(Context mContext, List<Feedback> mFeedbackList) {
        this.mContext = mContext;
        this.mFeedbackList = mFeedbackList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feedback,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Feedback feedback  = mFeedbackList.get(position);

        ((ItemViewHolder) holder).itemNameOfUser.setText(feedback.getFullname());
        ((ItemViewHolder) holder).itemSubject.setText(feedback.getSubject());
        ((ItemViewHolder) holder).itemContent.setText(feedback.getContent());
        ((ItemViewHolder) holder).itemEmail.setText(feedback.getEmail());
    }


    @Override
    public int getItemCount() {
        if (mFeedbackList != null){
            return mFeedbackList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView itemNameOfUser;
        private TextView itemSubject;
        private TextView itemContent;
        private TextView itemEmail;

        public ItemViewHolder( View itemView) {
            super(itemView);
            itemSubject = itemView.findViewById(R.id.tv_subject);
            itemNameOfUser = itemView.findViewById(R.id.tv_name_of_user);
            itemContent = itemView.findViewById(R.id.tv_content);
            itemEmail = itemView.findViewById(R.id.tv_email_user);
        }
    }

}
